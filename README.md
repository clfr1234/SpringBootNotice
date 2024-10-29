# SpringBootNotice
 스프링부트 게시판


rptlvks\src\main\resources\ 디렉터리에 
application.properties 파일 생성 후 
```
spring.application.name=rptlvks
server.port={포트번호}
spring.datasource.url=jdbc:mysql://localhost:3306/notice
spring.datasource.username={mysql id}
spring.datasource.password={mysql pwd}
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username={myMail (google)}
spring.mail.password={mailDevCode}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.timeout=5000
spring.mail.properties.mail.smtp.starttls.enable=true

```
입력 해주세요
