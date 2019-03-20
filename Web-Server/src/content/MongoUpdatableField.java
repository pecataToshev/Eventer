package content;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface MongoUpdatableField {
	boolean canBeEmpty() default false;
	boolean required() default true;
	boolean updatable() default true;
}
