# Hướng dẫn cài đặt, triển khai
Tài liệu này hướng dẫn bạn cài đặt mã nguồn, triển khai trên thiết bị di động và webservice

- [Hướng dẫn cài đặt, triển khai](#hướng-dẫn-cài-đặt-triển-khai)
  * [Chạy ứng dụng Android trên EDM Android Studio](#chạy-ứng-dụng-android-trên-edm-android-studio)
      - [Tải mã nguồn](#tải-mã-nguồn)
      - [Build trong Android Studio](#build-trong-android-studio)
  * [Cài đặt ứng dụng trên thiết bị di động](#cài-đặt-ứng-dụng-trên-thiết-bị-di-động)
  * [Triển khai webservice trên herokuapp](#triển-khai-webservice-trên-herokuapp)
      - [Kết nối herokupp với repo github](#kết-nối-herokupp-với-repo-github)
      - [Thẩm định các API bằng Postman _(không bắt buộc)_](#thẩm-định-các-api-bằng-postman-không-bắt-buộc)


## Chạy ứng dụng Android trên EDM Android Studio
Để thực hiện, bạn được cho là đã cài đặt [Android Studio](https://developer.android.com/studio) và [git](https://git-scm.com/) _(không bắt buộc)_

#### Tải mã nguồn
Từ commandline, nhập
```bash
# Clone this repository
$ git clone https://github.com/ngankhanh98/gps_tracker_app.git

# Show folder path
$ pwd 
```
Bạn nên nhận được đường dẫn của thư mục gps_tracker_app, chẳng hạn: `e\gps_tracker_app`. Ký tự đầu tiên là ổ đĩa.
#### Build trong Android Studio
Mở thư mục trong path bên trên. Run app bằng: <kbd>Shift</kbd> + <kbd>F10</kdb>. 

Bạn có hai lựa chọn: 
- Run trên thiết bị di động thật. Cấu hình thiết bị di động để thực hiện: [Android Studio > Run on real device](https://developer.android.com/training/basics/firstapp/running-app#RealDevice)
- Run trên máy ảo do Android Studio cung cấp: [Android Studio > Run on an emulator](https://developer.android.com/training/basics/firstapp/running-app#Emulator)

## Cài đặt ứng dụng trên thiết bị di động
Ứng dụng này hoạt động chuẩn nhất trên **Android 6.0 trở lên**. Hãy tải và cài đặt **.apk** trên thiết bị di động.

## Triển khai webservice trên herokuapp
Bạn cần là một trong contributor để thực hiện thao tác này. Hiện tại, vui lòng đọc các tài liệu liên quan hoặc yêu cầu trưởng dự án cung cấp một trong các tài khoản contributor

#### Kết nối herokupp với repo github
Trong Dashboard, chọn **New > Create new app**, đặt tên **App name**, nhấn **Create app**. Bạn sẽ được điều hướng tới Dashboard của app, tùy chọn như hình
![](https://raw.githubusercontent.com/ngankhanh98/Images/master/heroku.png)
![](https://raw.githubusercontent.com/ngankhanh98/Images/master/heroku2.png)
Sau khi deploy kết thúc, webservice của bạn đã triển khai thành công trên `https://<app-name>.herokuapp.com/`.
Webservice của nhóm đang triển khai tại `https://dacnpm-backend.herokuapp.com/`.
#### Thẩm định các API bằng Postman _(không bắt buộc)_
Bạn có thể cài đặt và sử dụng Postman để kiểm tra webservice.
