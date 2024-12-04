package page;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class YopmailPage {
        public static void main(String[] args) {
            // Thay thế "your_username" bằng địa chỉ email bạn muốn đọc (không cần @yopmail.com)
            String emailAddress = "huyle020597";

            try {
                // Tạo URL truy cập vào hộp thư YOPmail
                String url = "https://yopmail.com/wm";

                // Mở kết nối HTTP
                URL obj = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

                // Thiết lập phương thức GET
                connection.setRequestMethod("GET");

                // Kiểm tra mã phản hồi
                int responseCode = connection.getResponseCode();
                System.out.println("HTTP Response Code: " + responseCode);

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Đọc nội dung phản hồi
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    // Hiển thị nội dung HTML của hộp thư
                    System.out.println("Nội dung hộp thư:");
                    System.out.println(response.toString());
                } else {
                    System.out.println("Không thể kết nối tới YOPmail.");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

