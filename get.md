1.hibernate注解
    1.类
        @Entity
        @Table(name = "表名")
    2.主键，一般放在get方法上
        @Id
        @GeneratedValue(generator = "_native")
        @GenericGenerator(name = "_native", strategy = "native")
    3.普通键，一般放在get方法上
        可以不用使用注解，使用默认配置
        也可以使用注解，自定义配置：@Column(name = "cardNo", length = 50)
    4.不想在表中出现的键，一般放在get方法上
        @Transient
    5.多对一，一般放在get方法上
        @ManyToOne
        @JoinColumn(name = "外键字段名")     //也可以不用设置，双向一段设置就好
        可选：@Cascade(value = {CascadeType.SAVE_UPDATE})  默认是不会级联保存的
    6.一对多，一般放在get方法上
        @OneToMany(mappedBy="paper",fetch=FetchType.EAGER)
            mappedBy: 将维护主外键关系交给多的一方来维护，值为多端类的一端类属性的属性名
            fetch:加载多端的方式
                1、FetchType.LAZY：懒加载，加载一个实体时，定义懒加载的属性不会马上从数据库中加载。
                2、FetchType.EAGER：急加载，加载一个实体时，定义急加载的属性会立即从数据库中加载。
        @JoinColumn(name = "外键字段名")     //也可以不用设置，双向一段设置就好
2.遇到的坑
    问题：
        mysql用的是root用户，root用户下用很多数据库，其他数据库有和本次创建的表有重复的表名，则hibernate自动创建表可能不会成功。
    解决方法：
        <property name="hbm2ddl.auto">update改成create，然后再改成update</property>         
3.query.uniqueResult()：
    返回唯一值，如果未空则返回null
4.struts中action可以实现 XXXAware 接口，就可以拿到XXX。
    action类 implement ServletRequestAware、ServletResponseAware...
    private HttpServletRequest request;
    private HttpServletResponse response;
    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
    等set方法
5.登陆界面的实现
    table+背景图布局，