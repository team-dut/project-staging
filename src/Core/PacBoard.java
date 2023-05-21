package Core;

import ABC.BaseGhost;
import Background.SoundPlayer;
import Entities.*;
import Helpers.ImageHelper;
import UI.PacWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class PacBoard extends JPanel {
    private final Image foodImage = ImageIO.read(Files.newInputStream(Paths.get("resources/images/food.png")));
    private final Image gameOverImage = ImageIO.read(Files.newInputStream(Paths.get("resources/images/gameover.png")));
    private final Image gameClearImage = ImageIO.read(Files.newInputStream(Paths.get("resources/images/victory.png")));
    private final JLabel scoreboard;
    private final SoundPlayer siren;
    private final SoundPlayer pacmanSound;
    private final MapData mapData;

    private final PacWindow windowParent;
    private final Point ghostBase;
    private final int m_x;
    private final int m_y;
    private final int[][] map;
    private final Pacman pacman;
    private final boolean isCustom;
    private final Timer redrawTimer;
    private final ActionListener redrawAL;
    private final Image[] mapSegments;
    private final Image[] powerUpFoodImage;
    private ArrayList<Food> foods;
    private ArrayList<PowerUpFood> powerUpFoods;
    private ArrayList<BaseGhost> ghosts;
    private ArrayList<TeleportTunnel> teleports;
    private boolean isGameOver = false;
    private boolean isWin = false;
    private boolean drawScore = false;
    private boolean shouldClearScore = false;
    private int pendingScore = 0;
    private int score;
    private boolean shouldPlaySiren = false;

    public PacBoard(JLabel scoreboard, MapData md, PacWindow pw) throws IOException {
        this.setDoubleBuffered(true);

        this.scoreboard = scoreboard;
        this.windowParent = pw;
        this.m_x = md.getX();
        this.m_y = md.getY();
        this.mapData = md;
        this.map = md.getMap();
        this.isCustom = md.isCustom();
        this.ghostBase = md.getGhostBasePosition();

        this.pacman = new Pacman(md.getPacmanPosition().x, md.getPacmanPosition().y, this);
        addKeyListener(pacman);

        this.foods = new ArrayList<>();
        this.powerUpFoods = new ArrayList<>();
        this.ghosts = new ArrayList<>();
        this.teleports = new ArrayList<>();

        if (!isCustom) {
            for (int i = 0; i < m_x; i++) {
                for (int j = 0; j < m_y; j++) {
                    if (map[i][j] == 0)
                        foods.add(new Food(i, j));
                }
            }
        } else {
            foods = md.getFoods();
        }

        this.powerUpFoods = md.getPowerUpFoods();
        this.ghosts = new ArrayList<>();
        this.teleports = md.getTeleports();

        for (GhostData gd : md.getGhosts()) {
            switch (gd.getColor()) {
                case RED:
                    ghosts.add(new RedGhost(gd.getX(), gd.getY(), this));
                    break;
                case PINK:
                    ghosts.add(new PinkGhost(gd.getX(), gd.getY(), this));
                    break;
                case CYAN:
                    ghosts.add(new CyanGhost(gd.getX(), gd.getY(), this));
                    break;
            }
        }

        setLayout(null);
        setSize(20 * m_x, 20 * m_y);
        setBackground(Color.black);

        this.mapSegments = new Image[28];
        mapSegments[0] = null;

        for (int ms = 1; ms < 28; ms++) {
            try {
                mapSegments[ms] = ImageIO.read(Files.newInputStream(Paths.get("resources/images/map segments/" + ms + ".png")));
            } catch (Exception ignored) {
            }
        }

        powerUpFoodImage = new Image[5];
        for (int ms = 0; ms < 5; ms++) {
            try {
                powerUpFoodImage[ms] = ImageIO.read(Files.newInputStream(Paths.get("resources/images/food/" + ms + ".png")));
            } catch (Exception ignored) {
            }
        }

        // TODO: set to fixed fps value(s)
        // ex: 60fps ~= 17ms
        this.redrawAL = evt -> repaint();
        this.redrawTimer = new Timer(16, redrawAL);
        this.redrawTimer.start();

        this.siren = new SoundPlayer("siren.wav");
        this.pacmanSound = new SoundPlayer("pac6.wav");

        this.siren.start();
    }

    public MapData getMapData() {
        return mapData;
    }

    private void collisionTest() {
        Pacman pacman = getPacman();

        Rectangle pr = new Rectangle(
                (int) (pacman.getPixelPosition().getX() + 13),
                (int) (pacman.getPixelPosition().getY() + 13),
                2,
                2
        );

        BaseGhost ghostToRemove = null;

        for (BaseGhost g : getGhosts()) {
            Rectangle gr = new Rectangle(
                    (int) g.getPixelPosition().getX(),
                    (int) g.getPixelPosition().getY(),
                    28,
                    28
            );

            if (pr.intersects(gr)) {
                if (!g.getDead()) {
                    if (!g.getWeak()) {
                        getSiren().stop();
                        new SoundPlayer("pacman_lose.wav").start();
                        pacman.getMoveTimer().stop();
                        pacman.getAnimateTimer().stop();
                        g.getMoveTimer().stop();

                        setGameOver(true);
                        getScoreboard().setText("    Press R to try again !");

                        break;
                    } else {
                        new SoundPlayer("pacman_eatghost.wav").start();
                        setShouldDrawScore(true);
                        addPendingScore(1);

                        if (getGhostBase() != null)
                            g.die();
                        else
                            ghostToRemove = g;
                    }
                }
            }
        }

        if (ghostToRemove != null) getGhosts().remove(ghostToRemove);
    }

    private void update() {
        Food foodToEat = null;
        Pacman pacman = getPacman();

        //Check food eat
        for (Food f : getFoods()) {
            if (
                    pacman.getLogicalPosition().getX() == f.getPosition().getX() &&
                            pacman.getLogicalPosition().getY() == f.getPosition().getY()
            ) {
                foodToEat = f;
                break;
            }
        }

        if (foodToEat != null) {
            new SoundPlayer("pacman_eat.wav").start();
            getFoods().remove(foodToEat);
            addScore(10);
            getScoreboard().setText("    Score : " + getScore());

            if (getFoods().size() == 0) {
                getSiren().stop();
                getPacmanSound().stop();
                new SoundPlayer("pacman_intermission.wav").start();
                setWin(true);

                pacman.getMoveTimer().stop();

                for (BaseGhost g : getGhosts()) {
                    g.getMoveTimer().stop();
                }
            }
        }

        PowerUpFood puFoodToEat = null;

        for (PowerUpFood puf : getPowerUpFoods()) {
            if (
                    pacman.getLogicalPosition().getX() == puf.getPosition().getX() &&
                            pacman.getLogicalPosition().getY() == puf.getPosition().getY()
            ) {
                puFoodToEat = puf;
                break;
            }
        }

        if (puFoodToEat != null) {
            // TODO: unmagic the number
            if (puFoodToEat.getType() == 0) {
                getPowerUpFoods().remove(puFoodToEat);
                getSiren().stop();
                setShouldPlaySiren(true);
                getPacmanSound().start();
                for (BaseGhost g : getGhosts()) {
                    g.weaken();
                }
                setPendingScore(0);
            } else {
                new SoundPlayer("pacman_eatfruit.wav").start();
                getPowerUpFoods().remove(puFoodToEat);
                setPendingScore(1);
                setShouldDrawScore(true);
            }
        }

        for (BaseGhost g : getGhosts()) {
            if (
                    g.getDead() &&
                            g.getLogicalPosition().getX() == getGhostBase().getX() &&
                            g.getLogicalPosition().getY() == getGhostBase().getY()) {
                g.revive();
            }
        }

        for (TeleportTunnel tp : getTeleports()) {
            if (
                    pacman.getLogicalPosition().getX() == tp.getFrom().getX() &&
                            pacman.getLogicalPosition().getY() == tp.getFrom().getY() &&
                            pacman.getActiveMove() == tp.getReqMove()
            ) {
                pacman.setLogicalPosition(tp.getTo());
                pacman.getPixelPosition().setLocation(
                        pacman.getLogicalPosition().getX() * 28,
                        pacman.getLogicalPosition().getY() * 28
                );
            }
        }

        //Check isSiren
        boolean isSiren = true;
        for (BaseGhost g : getGhosts()) {
            if (g.getWeak()) {
                isSiren = false;
                break;
            }
        }

        if (isSiren) {
            getPacmanSound().stop();
            if (getShouldPlaySiren()) {
                setShouldPlaySiren(false);
                getSiren().start();
            }
        }
    }

    public boolean getShouldPlaySiren() {
        return shouldPlaySiren;
    }

    public void setShouldPlaySiren(boolean shouldPlaySiren) {
        this.shouldPlaySiren = shouldPlaySiren;
    }

    public SoundPlayer getPacmanSound() {
        return pacmanSound;
    }

    public ArrayList<TeleportTunnel> getTeleports() {
        return teleports;
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public ArrayList<PowerUpFood> getPowerUpFoods() {
        return powerUpFoods;
    }

    public Image getFoodImage() {
        return foodImage;
    }

    public Image[] getPowerUpFoodImage() {
        return powerUpFoodImage;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int[][] map = getMap();
        Image[] mapSegments = getMapSegments();

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);

        g2d.setColor(new Color(135, 205, 246));

        for (int i = 0; i < getMaxX(); i++)
            for (int j = 0; j < getMaxY(); j++)
                if (map[i][j] > 0)
                    g2d.drawImage(mapSegments[map[i][j]], 10 + i * 28, 10 + j * 28, null);

        g2d.setColor(new Color(204, 122, 122));

        for (Food f : getFoods())
            g2d.drawImage(
                    getFoodImage(),
                    (int) (10 + f.getPosition().getX() * 28),
                    (int) (10 + f.getPosition().getY() * 28),
                    null);

        g2d.setColor(new Color(204, 174, 168));
        for (PowerUpFood pf : getPowerUpFoods()) {
            g2d.drawImage(
                    getPowerUpFoodImage()[pf.getType()],
                    (int) (10 + pf.getPosition().getX() * 28),
                    (int) (10 + pf.getPosition().getY() * 28),
                    null
            );
        }

        Pacman pacman = getPacman();

        switch (pacman.getActiveMove()) {
            case NONE:
            case RIGHT:
                g2d.drawImage(
                        pacman.getPacmanImage(),
                        (int) (10 + pacman.getPixelPosition().getX()),
                        (int) (10 + pacman.getPixelPosition().getY()),
                        null);
                break;
            case LEFT:
                g2d.drawImage(
                        ImageHelper.flipHor(pacman.getPacmanImage()),
                        (int) (10 + pacman.getPixelPosition().getX()),
                        (int) (10 + pacman.getPixelPosition().getY()),
                        null
                );
                break;
            case DOWN:
                g2d.drawImage(
                        ImageHelper.rotate90(pacman.getPacmanImage()),
                        (int) (10 + pacman.getPixelPosition().getX()),
                        (int) (10 + pacman.getPixelPosition().getY()),
                        null
                );
                break;
            case UP:
                g2d.drawImage(ImageHelper.flipVer(
                                ImageHelper.rotate90(pacman.getPacmanImage())),
                        (int) (10 + pacman.getPixelPosition().getX()),
                        (int) (10 + pacman.getPixelPosition().getY()),
                        null
                );
                break;
        }

        for (BaseGhost gh : getGhosts()) {
            g2d.drawImage(
                    gh.getGhostImage(),
                    (int) (10 + gh.getPixelPosition().getX()),
                    (int) (10 + gh.getPixelPosition().getY()),
                    null);
        }

        if (getShouldClearScore()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setShouldDrawScore(false);
            setShouldClearScore(false);
        }

        if (getShouldDrawScore()) {
            g2d.setFont(new Font("Arial", Font.BOLD, 15));
            g2d.setColor(Color.yellow);

            int s = getPendingScore() * 100;

            g2d.drawString(
                    Integer.toString(s),
                    (int) (pacman.getPixelPosition().getX() + 13),
                    (int) (pacman.getPixelPosition().getY() + 50)
            );

            addScore(s);
            getScoreboard().setText("    Score : " + getScore());
            setShouldClearScore(true);
        }

        if (getGameOver()) {
            g2d.drawImage(
                    getGameOverImage(),
                    (int) (this.getSize().getWidth() / 2 - 315),
                    (int) (this.getSize().getHeight() / 2 - 75),
                    null
            );
        }

        if (getWin()) {
            g2d.drawImage(
                    getGameClearImage(),
                    (int) (this.getSize().getWidth() / 2 - 315),
                    (int) (this.getSize().getHeight() / 2 - 75),
                    null
            );
        }

        g2d.dispose();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int value) {
        setScore(getScore() + value);
    }

    public int getPendingScore() {
        return pendingScore;
    }

    public void setPendingScore(int pendingScore) {
        this.pendingScore = pendingScore;
    }

    public void addPendingScore(int value) {
        setPendingScore(getPendingScore() + value);
    }

    public JLabel getScoreboard() {
        return scoreboard;
    }

    public Image getGameOverImage() {
        return gameOverImage;
    }

    public Image getGameClearImage() {
        return gameClearImage;
    }

    public boolean getWin() {
        return isWin;
    }

    public void setWin(boolean win) {
        isWin = win;
    }

    public boolean getShouldDrawScore() {
        return drawScore;
    }

    public void setShouldDrawScore(boolean shouldDrawScore) {
        this.drawScore = shouldDrawScore;
    }

    public boolean getShouldClearScore() {
        return shouldClearScore;
    }

    public void setShouldClearScore(boolean shouldClearScore) {
        this.shouldClearScore = shouldClearScore;
    }

    public ArrayList<BaseGhost> getGhosts() {
        return ghosts;
    }

    public Image[] getMapSegments() {
        return mapSegments;
    }

    public boolean getCustom() {
        return isCustom;
    }

    @Override
    public void processEvent(AWTEvent ae) {

        if (ae.getID() == GameMessage.UPDATE) {
            update();
        } else if (ae.getID() == GameMessage.COLLISION_TEST) {
            if (!getGameOver()) {
                collisionTest();
            }
        } else if (ae.getID() == GameMessage.RESET) {
            if (getGameOver()) {
                try {
                    restart();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            super.processEvent(ae);
        }
    }

    public boolean getGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public void restart() throws IOException {
        getSiren().stop();

        PacWindow.getInstance().loadFromCustomMap(getMapData());
        getWindowParent().dispose();
    }

    public SoundPlayer getSiren() {
        return siren;
    }

    public PacWindow getWindowParent() {
        return windowParent;
    }

    public int[][] getMap() {
        return this.map;
    }

    public Point getGhostBase() {
        return ghostBase;
    }

    public Pacman getPacman() {
        return pacman;
    }

    public int getMaxX() {
        return m_x;
    }

    public int getMaxY() {
        return m_y;
    }
}