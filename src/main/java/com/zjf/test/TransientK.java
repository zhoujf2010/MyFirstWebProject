package com.zjf.test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * xml转换时，实体里面可以忽略处理的标识
 * 
 * @author komojoemary
 * @version [版本号, 2015-6-23]
 */
@Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TransientK
{
}
