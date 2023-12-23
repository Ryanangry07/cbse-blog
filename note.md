

### API 首页

GET http://localhost:8888/articles/hot

```
GET http://localhost:8888/articles/hot
{
	"code":0,
	"data":[
		{
			"id":1,
			"title":"Vue构建前台页面Vue构建前台页面Vue构建前台页面Vue构建前台页面"
		},
		{
			"id":25,
			"title":"first article"
		},
		{
			"id":10,
			"title":"Element相关"
		},
		{
			"id":9,
			"title":"Vue.js 是什么"
		}
	],
	"msg":"成功"
}
```

GET http://localhost:8888/articles/new

```
GET http://localhost:8888/articles/new
{
	"code":0,
	"data":[
		{
			"id":25,
			"title":"first article"
		},
		{
			"id":10,
			"title":"Element相关"
		},
		{
			"id":9,
			"title":"Vue.js 是什么"
		},
		{
			"id":1,
			"title":"Vue构建前台页面Vue构建前台页面Vue构建前台页面Vue构建前台页面"
		}
	],
	"msg":"成功"
}
```

GET http://localhost:8888/tags/hot

```
{
	"code":0,
	"data":[
		{
			"avatar":"/tag/vue.png",
			"id":7,
			"tagname":"Vue"
		},
		{
			"avatar":"/tag/html.png",
			"id":5,
			"tagname":"Html"
		},
		{
			"avatar":"/tag/css.png",
			"id":8,
			"tagname":"Css"
		},
		{
			"avatar":"/tag/java.png",
			"id":1,
			"tagname":"Java"
		},
		{
			"avatar":"/tag/js.png",
			"id":6,
			"tagname":"JavaScript"
		},
		{
			"avatar":"/tag/",
			"id":2,
			"tagname":"Spring"
		}
	],
	"msg":"成功"
}
```



GET http://localhost:8888/articles/listArchives

```
{
	"code":0,
	"data":[
		{
			"commentCounts":0,
			"count":1,
			"month":1,
			"summary":"",
			"title":"",
			"viewCounts":0,
			"weight":0,
			"year":2018
		},
		{
			"commentCounts":0,
			"count":2,
			"month":2,
			"summary":"",
			"title":"",
			"viewCounts":0,
			"weight":0,
			"year":2018
		},
		{
			"commentCounts":0,
			"count":1,
			"month":12,
			"summary":"",
			"title":"",
			"viewCounts":0,
			"weight":0,
			"year":2023
		}
	],
	"msg":"成功"
}
```

GET http://localhost:8888/articles?pageNumber=1&pageSize=5&name=a.createDate&sort=desc

```
{
	"code":0,
	"data":[
		{
			"author":{
				"nickname":"root"
			},
			"commentCounts":0,
			"createDate":"2023.12.09 13:18",
			"id":25,
			"summary":"This is root's first article",
			"tags":[
				{
					"tagname":"Java"
				}
			],
			"title":"first article",
			"viewCounts":6,
			"weight":0
		},
		{
			"author":{
				"nickname":"史明辉"
			},
			"commentCounts":0,
			"createDate":"2018.02.01 14:47",
			"id":10,
			"summary":"本节将介绍如何在项目中使用 Element。",
			"tags":[
				{
					"tagname":"Vue"
				},
				{
					"tagname":"Css"
				},
				{
					"tagname":"Html"
				},
				{
					"tagname":"JavaScript"
				}
			],
			"title":"Element相关",
			"viewCounts":5,
			"weight":0
		},
		{
			"author":{
				"nickname":"史明辉"
			},
			"commentCounts":0,
			"createDate":"2018.02.01 14:37",
			"id":9,
			"summary":"Vue (读音 /vjuː/，类似于 view) 是一套用于构建用户界面的渐进式框架。",
			"tags":[
				{
					"tagname":"Vue"
				}
			],
			"title":"Vue.js 是什么",
			"viewCounts":3,
			"weight":0
		},
		{
			"author":{
				"nickname":"史明辉"
			},
			"commentCounts":2,
			"createDate":"2018.01.31 13:16",
			"id":1,
			"summary":"Vue构建前台页面过程1Vue构建前台页面过程2Vue构建前台页面过程3Vue构建前台页面过程4Vue构建前台页面过程5Vue构建前台页面过程6Vue构建前台页面过程7Vue构建前台页面过程8",
			"tags":[
				{
					"tagname":"Vue"
				},
				{
					"tagname":"Html"
				},
				{
					"tagname":"Css"
				}
			],
			"title":"Vue构建前台页面Vue构建前台页面Vue构建前台页面Vue构建前台页面",
			"viewCounts":54,
			"weight":0
		}
	],
	"msg":"成功"
}
```



### API 登录

POST http://localhost:8888/login

```
Payload: {"account":"root","password":"root"}

Response:
{
	"code":0,
	"data":{
		"Oauth-Token":"0ad26898-9261-4fc7-8ce2-21fdc2da8564"
	},
	"msg":"成功"
}
```



GET http://localhost:8888/users/currentUser

```
{
	"code":0,
	"data":{
		"account":"root",
		"avatar":"/static/user/user_6.png",
		"id":16,
		"nickname":"root"
	},
	"msg":"成功"
}
```





### API 文章分类标签

GET http://localhost:8888/categorys/detail

```
{
	"code":0,
	"data":[
		{
			"articles":3,
			"avatar":"/category/front.png",
			"categoryname":"前端",
			"description":"",
			"id":1
		},
		{
			"articles":0,
			"avatar":"/category/back.png",
			"categoryname":"后端",
			"description":"",
			"id":2
		},
		{
			"articles":1,
			"avatar":"/category/lift.jpg",
			"categoryname":"生活",
			"description":"",
			"id":3
		},
		{
			"articles":0,
			"avatar":"/category/database.png",
			"categoryname":"数据库",
			"description":"",
			"id":4
		},
		{
			"articles":0,
			"avatar":"/category/language.png",
			"categoryname":"编程语言",
			"description":"",
			"id":5
		}
	],
	"msg":"成功"
}
```

GET http://localhost:8888/tags/detail

```
{
	"code":0,
	"data":[
		{
			"articles":1,
			"avatar":"/tag/java.png",
			"id":1,
			"tagname":"Java"
		},
		{
			"articles":0,
			"avatar":"/tag/",
			"id":2,
			"tagname":"Spring"
		},
		{
			"articles":0,
			"avatar":"/tag/hibernate.svg",
			"id":3,
			"tagname":"Hibernate"
		},
		{
			"articles":0,
			"avatar":"/tag/maven.png",
			"id":4,
			"tagname":"Maven"
		},
		{
			"articles":2,
			"avatar":"/tag/html.png",
			"id":5,
			"tagname":"Html"
		},
		{
			"articles":1,
			"avatar":"/tag/js.png",
			"id":6,
			"tagname":"JavaScript"
		},
		{
			"articles":3,
			"avatar":"/tag/vue.png",
			"id":7,
			"tagname":"Vue"
		},
		{
			"articles":2,
			"avatar":"/tag/css.png",
			"id":8,
			"tagname":"Css"
		}
	],
	"msg":"成功"
}
```





### API 文章归档

GET http://localhost:8888/articles/listArchives

```
{
	"code":0,
	"data":[
		{
			"commentCounts":0,
			"count":1,
			"month":1,
			"summary":"",
			"title":"",
			"viewCounts":0,
			"weight":0,
			"year":2018
		},
		{
			"commentCounts":0,
			"count":2,
			"month":2,
			"summary":"",
			"title":"",
			"viewCounts":0,
			"weight":0,
			"year":2018
		},
		{
			"commentCounts":0,
			"count":1,
			"month":12,
			"summary":"",
			"title":"",
			"viewCounts":0,
			"weight":0,
			"year":2023
		}
	],
	"msg":"成功"
}
```

GET http://localhost:8888/articles?pageNumber=1&pageSize=5&name=a.createDate&sort=desc

```
GET Payload: pageNumber=1&pageSize=5&name=a.createDate&sort=desc

Response:
{
	"code":0,
	"data":[
		{
			"author":{
				"nickname":"root"
			},
			"commentCounts":0,
			"createDate":"2023.12.09 13:18",
			"id":25,
			"summary":"This is root's first article",
			"tags":[
				{
					"tagname":"Java"
				}
			],
			"title":"first article",
			"viewCounts":6,
			"weight":0
		},
		{
			"author":{
				"nickname":"史明辉"
			},
			"commentCounts":0,
			"createDate":"2018.02.01 14:47",
			"id":10,
			"summary":"本节将介绍如何在项目中使用 Element。",
			"tags":[
				{
					"tagname":"Vue"
				},
				{
					"tagname":"Css"
				},
				{
					"tagname":"Html"
				},
				{
					"tagname":"JavaScript"
				}
			],
			"title":"Element相关",
			"viewCounts":5,
			"weight":0
		},
		{
			"author":{
				"nickname":"史明辉"
			},
			"commentCounts":0,
			"createDate":"2018.02.01 14:37",
			"id":9,
			"summary":"Vue (读音 /vjuː/，类似于 view) 是一套用于构建用户界面的渐进式框架。",
			"tags":[
				{
					"tagname":"Vue"
				}
			],
			"title":"Vue.js 是什么",
			"viewCounts":3,
			"weight":0
		},
		{
			"author":{
				"nickname":"史明辉"
			},
			"commentCounts":2,
			"createDate":"2018.01.31 13:16",
			"id":1,
			"summary":"Vue构建前台页面过程1Vue构建前台页面过程2Vue构建前台页面过程3Vue构建前台页面过程4Vue构建前台页面过程5Vue构建前台页面过程6Vue构建前台页面过程7Vue构建前台页面过程8",
			"tags":[
				{
					"tagname":"Vue"
				},
				{
					"tagname":"Html"
				},
				{
					"tagname":"Css"
				}
			],
			"title":"Vue构建前台页面Vue构建前台页面Vue构建前台页面Vue构建前台页面",
			"viewCounts":54,
			"weight":0
		}
	],
	"msg":"成功"
}
```



```
GET Payload: pageNumber=1&pageSize=5&name=a.createDate&sort=desc&year=2023&month=12

Response:
{
	"code":0,
	"data":[
		{
			"author":{
				"nickname":"root"
			},
			"commentCounts":0,
			"createDate":"2023.12.09 13:18",
			"id":25,
			"summary":"This is root's first article",
			"tags":[
				{
					"tagname":"Java"
				}
			],
			"title":"first article",
			"viewCounts":6,
			"weight":0
		}
	],
	"msg":"成功"
}
```





### API 写文章

GET http://localhost:8888/categorys

```
{
	"code":0,
	"data":[
		{
			"avatar":"/category/front.png",
			"categoryname":"前端",
			"description":"",
			"id":1
		},
		{
			"avatar":"/category/back.png",
			"categoryname":"后端",
			"description":"",
			"id":2
		},
		{
			"avatar":"/category/lift.jpg",
			"categoryname":"生活",
			"description":"",
			"id":3
		},
		{
			"avatar":"/category/database.png",
			"categoryname":"数据库",
			"description":"",
			"id":4
		},
		{
			"avatar":"/category/language.png",
			"categoryname":"编程语言",
			"description":"",
			"id":5
		}
	],
	"msg":"成功"
}
```

GET http://localhost:8888/tags

```
{
	"code":0,
	"data":[
		{
			"avatar":"/tag/java.png",
			"id":1,
			"tagname":"Java"
		},
		{
			"avatar":"/tag/",
			"id":2,
			"tagname":"Spring"
		},
		{
			"avatar":"/tag/hibernate.svg",
			"id":3,
			"tagname":"Hibernate"
		},
		{
			"avatar":"/tag/maven.png",
			"id":4,
			"tagname":"Maven"
		},
		{
			"avatar":"/tag/html.png",
			"id":5,
			"tagname":"Html"
		},
		{
			"avatar":"/tag/js.png",
			"id":6,
			"tagname":"JavaScript"
		},
		{
			"avatar":"/tag/vue.png",
			"id":7,
			"tagname":"Vue"
		},
		{
			"avatar":"/tag/css.png",
			"id":8,
			"tagname":"Css"
		}
	],
	"msg":"成功"
}
```



### API 文章详情

GET http://localhost:8888/articles/view/10

```
{
	"code":0,
	"data":{
		"author":{
			"avatar":"/user/admin.png",
			"id":1,
			"nickname":"史明辉"
		},
		"body":{
			"content":"## 快速上手\n\n本节将介绍如何在项目中使用 Element。\n\n### 使用 Starter Kit\n我们提供了通用的项目模板，你可以直接使用。对于 Laravel 用户，我们也准备了相应的模板，同样可以直接下载使用。\n\n如果不希望使用我们提供的模板，请继续阅读。\n\n### 使用 vue-cli\n\n我们还可以使用 vue-cli 初始化项目，命令如下：\n\n```language\n> npm i -g vue-cli\n> mkdir my-project && cd my-project\n> vue init webpack\n> npm i && npm i element-ui\n```\n\n### 引入 Element\n你可以引入整个 Element，或是根据需要仅引入部分组件。我们先介绍如何引入完整的 Element。\n\n#### 完整引入\n在 main.js 中写入以下内容：\n```javascript\nimport Vue from 'vue'\nimport ElementUI from 'element-ui'\nimport 'element-ui/lib/theme-chalk/index.css'\nimport App from './App.vue'\n\nVue.use(ElementUI)\n\nnew Vue({\n  el: '#app',\n  render: h => h(App)\n})\n\n```\n以上代码便完成了 Element 的引入。需要注意的是，样式文件需要单独引入。\n\n#### 按需引入\n借助 babel-plugin-component，我们可以只引入需要的组件，以达到减小项目体积的目的。\n\n首先，安装 babel-plugin-component：\n\n",
			"id":21
		},
		"category":{
			"avatar":"/category/front.png",
			"categoryname":"前端",
			"description":"",
			"id":1
		},
		"commentCounts":0,
		"createDate":"2018.02.01 14:47",
		"id":10,
		"summary":"本节将介绍如何在项目中使用 Element。",
		"tags":[
			{
				"id":7,
				"tagname":"Vue"
			},
			{
				"id":8,
				"tagname":"Css"
			},
			{
				"id":5,
				"tagname":"Html"
			},
			{
				"id":6,
				"tagname":"JavaScript"
			}
		],
		"title":"Element相关",
		"viewCounts":6,
		"weight":0
	},
	"msg":"成功"
}
```

GET http://localhost:8888/comments/article/10

```
{
	"code":0,
	"data":[
		{
			"author":{
				"avatar":"/static/user/user_6.png",
				"id":16,
				"nickname":"root"
			},
			"childrens":[
				{
					"author":{
						"avatar":"/static/user/user_6.png",
						"id":16,
						"nickname":"root"
					},
					"childrens":[],
					"content":"really????",
					"createDate":"2023.12.23 07:33",
					"id":2,
					"level":"1"
				}
			],
			"content":"You are my hero ok?",
			"createDate":"2023.12.23 07:33",
			"id":1,
			"level":"0"
		}
	],
	"msg":"成功"
}
```







### API 发表评论

POST http://localhost:8888/comments/create/change

```
Payload: {"article":{"id":10},"content":"You are my hero ok?"}

Response:
{
	"code":0,
	"data":{
		"author":{
			"avatar":"/static/user/user_6.png",
			"id":16,
			"nickname":"root"
		},
		"content":"You are my hero ok?",
		"createDate":"2023.12.23 07:33",
		"id":1,
		"level":"0"
	},
	"msg":"成功"
}
```

POST http://localhost:8888/comments/create/change

```
Payload: {"article":{"id":10},"parent":{"id":1},"toUser":"","content":"really????"}

Response:
{
	"code":0,
	"data":{
		"author":{
			"avatar":"/static/user/user_6.png",
			"id":16,
			"nickname":"root"
		},
		"content":"really????",
		"createDate":"2023.12.23 07:33",
		"id":2,
		"level":"1",
		"parent":{
			"content":"",
			"id":1,
			"level":""
		}
	},
	"msg":"成功"
}
```

POST http://localhost:8888/comments/create/change

```
Payload:
{"article":{"id":10},"parent":{"id":1},"toUser":{"avatar":"/static/user/user_6.png","id":16,"nickname":"root"},"content":"MAYBE YES"}

Response:
{
	"code":0,
	"data":{
		"author":{
			"avatar":"/static/user/user_6.png",
			"id":16,
			"nickname":"root"
		},
		"content":"MAYBE YES",
		"createDate":"2023.12.23 07:38",
		"id":3,
		"level":"2",
		"parent":{
			"content":"",
			"id":1,
			"level":""
		},
		"toUser":{
			"avatar":"/static/user/user_6.png",
			"id":16,
			"nickname":"root"
		}
	},
	"msg":"成功"
}
```



### Could not autowire.No beans of ‘ ‘ type found

```
Method 1. @Autowired => @Resource

or

Method 2. 降低安全级别
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210317124555607.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xvbGVyMTU=,size_16,color_FFFFFF,t_70) 

### MybatisPlus 获取 主键id

```
User user = new User();
user.setName("犬小哈");
user.setAge(30);
user.setGender(1);

userMapper.insert(user);

// 获取插入数据的主键 ID
Long id = user.getId();

System.out.println("id:" + id);
```



### 配置IDEA一键运行

![1703275192612](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\1703275192612.png) 