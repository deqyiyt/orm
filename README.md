# orm
整合mybatis和hibernate持久化框架

在service层调用dao时，mybatis和hibernate的super接口用同一个
目前仅扩展了mybatis和hibernate，按照这种方式可以扩展其他orm框架，如springJdbc等

test中有很详尽的使用范例可供参考

mybatis的核心代码通过tk.mybatis.mapper实现
