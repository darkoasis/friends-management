package sg.com.spgroup.friendsmgmt.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.annotation.JsonFormat;

@Target( { ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD,
        ElementType.PARAMETER, ElementType.TYPE } )
@Retention( RetentionPolicy.RUNTIME )
@JacksonAnnotationsInside
@JsonFormat( shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.SSS a Z", timezone = "Asia/Singapore" )
public @interface JsonDateFormat
{

}
