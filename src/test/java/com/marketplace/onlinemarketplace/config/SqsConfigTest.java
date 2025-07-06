package com.marketplace.onlinemarketplace.config;

import com.amazonaws.services.sqs.AmazonSQS;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class SqsConfigTest {

    @Test
    void givenProperties_whenCreatingSqsBean_thenNotNull() {
        sqsConfig config = new sqsConfig();
        ReflectionTestUtils.setField(config, "accessKey", "test-access-key");
        ReflectionTestUtils.setField(config, "secretKey", "test-secret-key");
        ReflectionTestUtils.setField(config, "region", "us-west-2");

        AmazonSQS sqs = config.sqs();

        assertNotNull(sqs, "The SQS client should not be null");
    }

    @Test
    void whenContextInitialized_thenSqsBeanIsAvailable() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().getSystemProperties().put("AWS_ACCESS_KEY", "access");
        context.getEnvironment().getSystemProperties().put("AWS_SECRET_KEY", "secret");
        context.getEnvironment().getSystemProperties().put("AWS_SQS_REGION", "us-east-1");
        context.register(sqsConfig.class);
        context.refresh();

        AmazonSQS sqs = context.getBean(AmazonSQS.class);
        assertNotNull(sqs, "The SQS client bean should be available in the context");

        context.close();
    }
}