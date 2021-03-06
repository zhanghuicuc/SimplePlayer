/*
 * 接口(interface)中，所有的方法必须都是抽象的，不能有方法体，它比抽象类更加“抽象”
 * 接口用于指定一个类必须做什么，而不是规定它如何去做。
 *
 * 现实中也有很多接口的实例，比如说串口电脑硬盘，Serial ATA委员会指定了Serial ATA 2.0规范，这种规范就是接口。
 * Serial ATA委员会不负责生产硬盘，只是指定通用的规范。
 * 希捷、日立、三星等生产厂家会按照规范生产符合接口的硬盘，这些硬盘就可以实现通用化
 * 如果正在用一块160G日立的串口硬盘，现在要升级了，可以购买一块320G的希捷串口硬盘，安装上去就可以继续使用了。
 *
 * 行为模型应该总是通过接口而不是抽象类定义，所以通常是优先选用接口，尽量少用抽象类。
 *
 * 接口的一些特性
 * 1、接口中只能定义抽象方法，这些方法默认为 public abstract 的，因而在声明方法时可以省略这些修饰符。
 * 试图在接口中定义实例变量、非抽象的实例方法及静态方法，都是非法的
 * 2、接口中没有构造方法，不能被实例化。
 * 3、一个接口不实现另一个接口，但可以继承多个其他接口。接口的多继承特点弥补了类的单继承
 *
 * 为什么使用接口
 * 接口提供了关联以及方法调用上的可插入性，软件系统的规模越大，生命周期越长，接口使得软件系统的灵活性和可扩展性，可插入性方面得到保证。
 * 接口在面向对象的 Java 程序设计中占有举足轻重的地位。事实上在设计阶段最重要的任务之一就是设计出各部分的接口，然后通过接口的组合，形成程序的基本框架结构。
 *
 * 接口必须通过类来实现(implements)它的抽象方法，然后再实例化类。类实现接口的关键字为implements。
 * 如果一个类不能实现该接口的所有抽象方法，那么这个类必须被定义为抽象方法。
 * 不允许创建接口的实例，但允许定义接口类型的引用变量，该变量指向了实现接口的类的实例。
 * 实现接口的格式如下：
 * 修饰符 class 类名 extends 父类 implements 多个接口 {
 * 实现方法
 * }
 *
 * 接口作为类型使用
 * 接口作为引用类型来使用，任何实现该接口的类的实例都可以存储在该接口类型的变量中，
 * 通过这些变量可以访问类中所实现的接口中的方法，Java 运行时系统会动态地确定应该使用哪个类中的方法，实际上是调用相应的实现类的方法。
 *
 * DownloadManagerCallback
 * DownloadManager回调接口
 * + segmentRetreived(Segment segment)已经获得segment.content之后的回调函数，即下一步要干什么
 * */

package com.zhanghui.simpleplayer;

public interface DownloadManagerCallback {
	public void segmentRetreived(Segment segment);
}
