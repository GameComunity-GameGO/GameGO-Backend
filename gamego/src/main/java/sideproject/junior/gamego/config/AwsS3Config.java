// package sideproject.junior.gamego.config;

// import com.amazonaws.auth.AWSStaticCredentialsProvider;
// import com.amazonaws.auth.BasicAWSCredentials;
// import com.amazonaws.services.s3.AmazonS3Client;
// import com.amazonaws.services.s3.AmazonS3ClientBuilder;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class AwsS3Config {

//    @Value("${ackey}")
//    private String accessKey;

//    @Value("${sckey}")
//    private String secretKey;

//    @Value("${cloud.aws.region.static}")
//    private String region;

//    @Bean
//    public AmazonS3Client amazonS3Client() {

//        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);

//        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
//                .withRegion(region)
//                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
//                .build();
//    }
// }
