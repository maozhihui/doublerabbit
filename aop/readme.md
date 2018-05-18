#核心概念
AOP 面向切面编程，如何理解切面？它包含了以下几个核心概念。
 - 切面（Aspect）切面是通知与切入点的结合，指明了在哪里做事，做什么事；
 - 通知（Advice）是指具体要做的功能，要做的事情，体现为切面中的一段方法；
 - 连接点（JoinPoint）连接定是连着两方，一方是切入点，一方是通知，相当于做着桥接功能一样，常用的连接点有before,after,AfterThrowing,AfterReturning几种；
 - 切入点（PointCut）是指想在哪些类或类中的某些方法上实施切面功能，这个点就是切入点；  

# PointCut
PointCut使用的最多的就是其中的表达式`Expression`，该表达式可以为以下各种形式。
Pointcut 可以有下列方式来定义或者 通过 && || 和 ! 方式进行组合
## 切入点类型
>args() 使用`args(参数类型列表)`匹配当前执行的方法传入的参数为指定类型的执行方法，注意是匹配传入的参数类型，不是匹配方法签名的参数类型；参数类型列表中的参数必须是类型全限定名，通配符不支持；args属于动态切入点，这种切入点开销非常大，非特殊情况最好不要使用。

>@args() 使用`@args()`匹配当前执行的方法传入的参数持有指定注解的执行；注解类型也必须是全限定类型名；

>@target() 使用`@target(注解类型)`匹配当前目标对象类型的执行方法，其中目标对象持有指定的注解；注解类型也必须是全限定类型名；

>@within() 使用`@within(注解类型)`匹配所以持有指定注解类型内的方法；注解类型也必须是全限定类型名；

>@annotation() 使用`@annotation(注解类型)`匹配当前执行方法持有指定注解的方法；注解类型也必须是全限定类型名；

>reference pointcut 表示引用其他命名切入点，只有@ApectJ风格支持，Schema风格不支持，其实就是本切面类中的方法；

>within 使用`within(类型表达式)`匹配指定类型内的方法执行; 

>@execution() 使用`execution(方法表达式)`匹配方法执行；

## 通配符
了解下AspectJ类型匹配的通配符如下。
 * *：匹配任何数量字符；
 * ..：匹配任何数量字符的重复，如在类型模式中匹配任何数量子包；而在方法参数模式中匹配任何数量参数。
 * +：匹配指定类型的子类型；仅能作为后缀放在类型模式后边。

## 方法表达式
方法表达式`execution(modifiers-pattern? ret-type-pattern declaring-type-pattern? name-pattern(param-pattern) throws-pattern?)`，其中打问号的字段代表可选字段，各个字段含义如下。
>modifiers-pattern 修饰类型，可选值：public,protected,private,*；*表示全部；

>ret-type-pattern 返回类型，所有可以作为返回类型的名字，自定义类可以写包的路径（用正则表达式匹配），*表示全部；

>declaring-type-pattern 

>name-pattern 指定方法名,`*`表示全部,set*,代表以set开头的所有方法;

>param-pattern 指定方法参数(声明的类型),(..)代表所有参数,(`*`)代表一个参数,(`*`,String)代表第一个参数为任何值,第二个为String类型; 

>throws-pattern 


# 参考文献
 - [AspectJ切入点语法详解](http://sishuok.com/forum/posts/list/281.html)
 - [Spring AOP中pointcut expression表达式解析](https://www.cnblogs.com/panie2015/p/5889729.html)

 