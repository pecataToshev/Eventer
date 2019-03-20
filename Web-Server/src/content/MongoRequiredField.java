package content;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface MongoRequiredField {
	boolean required() default true;
	boolean unique() default false;
}
