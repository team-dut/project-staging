 # Team Manual
 
 Cái này là https://docs.github.com/en/get-started/quickstart/contributing-to-projects nhưng được tinh gọn hết mức cho newbie.
 
 ## Cách sửa code
 
 ### 1. Kéo code về
 Vì write access sẽ bị khóa cho mọi người nên chúng ta sẽ dùng cách chuyên nghiệp hơn - Pull Request:
 
 Đầu tiên, fork lại project này bằng nút phía trên bên phải:
 
 ![image](https://user-images.githubusercontent.com/41561710/234154582-670bf606-ed67-4c11-ac5d-43fe2ec8f31c.png)
 
 Nhớ make sure mình đang ở project gốc (https://github.com/team-dut/project-staging)

Nó sẽ hỏi mình clone về đâu, cứ mặc định về chỗ gốc - profile của chính mình.
 
 Chờ 1 tí, nó sẽ nhảy vào cái repo mình vừa fork về. Nhìn lên góc trên bên trái thấy giống như này là đúng
 
 *forked from team-dut/project-staging*

![image](https://user-images.githubusercontent.com/41561710/234156106-beebd986-c45e-4ae7-b12e-25936f059da9.png)

Giờ mọi người có thể sửa code trong máy của chính mình **từ cái repository vừa fork về**

> https://docs.github.com/en/get-started/quickstart/contributing-to-projects#making-and-pushing-changes

> ℹ️ Nên nhớ là ghi commit message cho chỉn chu vào, không phải "Add files", "Create a.txt", ... mà nên "Force using NVIDIA on game", "Refactor Pacman.java to use Singleton design pattern"

Sửa xong quay lại trang chính sẽ có 1 đoạn như này là OK!

![image](https://user-images.githubusercontent.com/41561710/234156433-a4c28388-c860-4417-bc75-e5a37bea4db3.png)

> :warning: nhớ `SYNC FORK` khi thấy chữ **behind**, trừ khi có thông báo khác.

### 2. Submit thay đổi của mình

Quay lại GitHub repo nơi mình vừa fork về, sẽ có nút `Contribute`

![image](https://user-images.githubusercontent.com/41561710/234156078-56d70df7-66bd-4d6e-8756-ff958b462242.png)

Chọn `Open pull request`:

![image](https://user-images.githubusercontent.com/41561710/234156647-c73a1aa2-a226-4a96-abe4-3a676020b84d.png)

Nó sẽ nhảy ra trang giống thế này

![image](https://user-images.githubusercontent.com/41561710/234157095-45a59f25-7b98-40df-947c-91871ab5ad03.png)

- Phần trên là title cho Pull Request

- Phần dưới là nội dung cho Pull Request

> :warning: Hiển nhiên là vẫn phải ghi chỉn chu vào!!!

Xong rồi thì bấm nút `Create pull request` và chờ 1 admin và 1 mod nào đó xem code rồi merge vào.

> ℹ️ Nên nhớ là vẫn phải check thường xuyên để theo dõi update (yêu cầu sửa code, comment trong Pull Request đó, và tình trạng của Pull Request - accepted/rejected)
