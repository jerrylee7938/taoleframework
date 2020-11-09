package spring;

import io.swagger.annotations.ApiOperation;

import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置类
 * @author zhangqianlong
 * @date 2017年8月17日下午3:37:25
 */
@EnableSwagger2
// Loads the spring beans required by the framework
public class SwaggerConfig
{
	@Bean
    public Docket addDocket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        Contact contact = new Contact("", "", "");
        ApiInfo apiInfo = new ApiInfo("Portal服务", "WEB API文档", "V1.0", "", contact, "", "");
        docket.apiInfo(apiInfo);
        docket.select().apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).build();
        return docket;
    }
}