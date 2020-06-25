package kg.sabyrov.terrafit.config;

import com.google.common.base.Optional;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;

import javax.validation.constraints.Email;

import static springfox.bean.validators.plugins.Validators.annotationFromBean;

public class EmailAnnotationPlugin implements ModelPropertyBuilderPlugin {
    @Override
    public void apply(ModelPropertyContext modelPropertyContext) {
        Optional<Email> email = annotationFromBean(modelPropertyContext, Email.class);
        if (email.isPresent()) {
            modelPropertyContext.getBuilder().pattern(email.get().regexp());
            modelPropertyContext.getBuilder().example("abc@email.com");
        }
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }
}
