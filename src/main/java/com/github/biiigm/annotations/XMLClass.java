package com.github.biiigm.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/***
 * This annotation is used by the parser to identify the node name which
 * contains the nodes
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface XMLClass {
    String name() default "item";
}
