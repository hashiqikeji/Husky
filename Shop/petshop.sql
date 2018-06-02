


DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
	`vid` varchar(50) NOT NULL,  /*如果你不想字段为 NULL 可以设置字段的属性为 NOT NULL， 在操作数据库时如果输入该字段的数据为NULL ，就会报错*/
	`vname` varchar(50) DEFAULT NULL, /*默认为空*/
	PRIMARY KEY (`vid`)          /*PRIMARY KEY关键字用于定义列为主键。 您可以使用多列来定义主键，列间以逗号分隔。*/
) ENGINE=InnoDB DEFAULT CHARSET=utf8; /*ENGINE 设置存储引擎，CHARSET 设置编码。*/

 /*LOCK TABLES `category` WRITE;如果一个线程在一个表上得到一个WRITE锁，
                          那么只有拥有这个锁的线程可以从表中读取和写表。
						  其它的线程被阻塞.
						  防止他人写入和读取你设定的表内容,只有被授权的用户能读取和写入
						  */
/*insert  into `category`(`vid`,`vname`) values
(`1`,`狗狗`),
(`2`,`猫`),
(`3`,`其它`);


DROP TABLE IF EXISTS `orderitem`;
*/

CREATE TABLE `orderitem`(
	`itemid` varchar(50)  NOT NULL,
	`count`  int(11)  DEFAULT NULL,
	`subtotal` double DEFAULT NULL,
	`pid` varchar(50) DEFAULT NULL,
	`oid` varchar(50) DEFAULT NULL,
	PRIMARY KEY(`itemid`),  /*主键*/
	KEY `fk_0001` (`pid`),  /*表级约束语法 [CONSTRAINT 约束名]    FOREIGN KEY表示外键*/
	KEY `fk_0002` (`oid`),
	CONSTRAINT `fk_0001` FOREIGN KEY (`pid`) REFERENCES `product` (`pid`), /*一个表中的 FOREIGN KEY 指向另一个表中的 PRIMARY KEY*/
	CONSTRAINT `fk_0002` FOREIGN KEY (`oid`) REFERENCES `orders` (`oid`)      /*FOREIGN KEY 约束⽤于预防破坏表之间连接的行为*/
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`(
	`oid` varchar(50) NOT NULL,
	`ordertime` datetime DEFAULT NULL,
	`total` double DEFAULT NULL,
	`state` int(10) DEFAULT NULL,
	`address` varchar(50) DEFAULT NULL,
	`name` varchar(30) DEFAULT NULL,
	`telephone` varchar(30) DEFAULT NULL,
	`uid` varchar(50) DEFAULT NULL,
	PRIMARY KEY `oid`
) ENGINE=InnoDB DEFAULT CHARSET=utf8;





DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`(
	`pid` varchar(50) NOT NULL,
	`pname` varchar(50) DEFAULT NULL,
	`market_price` double DEFAULT NULL,
	`discount_price` double DEFAULT NULL,
	`pimage` varchar(200) DEFAULT NULL,
	`pdate` date DEFAULT NULL,
	`status` int(10) DEFAULT NULL,
	`details` varchar(255) DEFAULT NULL,
	`pflag`  int(11) DEFAULT NULL,
	`vid` varchar(50) DEFAULT NULL,
	PRIMARY KEY (`pid`),
	KEY `pkey_001` (`vid`),
	constraint `pkey_001` foreign key (`vid`) references `category` (`vid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `product`VALUES 
(`1`,`阿富汗猎犬`,3000 , 2000,`dog/阿富汗猎犬.jpg`,`2015-05-06`, 1,`阿富汗猎犬是一种贵族犬，是现存的最古老的猎犬犬种之一，它外貌高贵，态度超然，没有任何平凡或粗糙的痕迹。`, 0,`1`),
(`2`,``, , ,``,``, ,``, ,``),
(`3`,``, , ,``,``, ,``, ,``),
(`4`,``, , ,``,``, ,``, ,``),
(`5`,``, , ,``,``, ,``, ,``),
(`6`,``, , ,``,``, ,``, ,``),
(`7`,``, , ,``,``, ,``, ,``),
(`8`,``, , ,``,``, ,``, ,``),
(`9`,``, , ,``,``, ,``, ,``),
(`10`,``, , ,``,``, ,``, ,``),
(`11`,``, , ,``,``, ,``, ,``),
(`12`,``, , ,``,``, ,``, ,``),
(`13`,``, , ,``,``, ,``, ,``),
(`14`,``, , ,``,``, ,``, ,``),
(`15`,``, , ,``,``, ,``, ,``),
(`16`,``, , ,``,``, ,``, ,``),
(`17`,``, , ,``,``, ,``, ,``),
(`18`,``, , ,``,``, ,``, ,``),
(`19`,``, , ,``,``, ,``, ,``),
(`20`,``, , ,``,``, ,``, ,``),
(`21`,``, , ,``,``, ,``, ,``),
(`22`,``, , ,``,``, ,``, ,``),
(`23`,``, , ,``,``, ,``, ,``),
(`24`,``, , ,``,``, ,``, ,``),
(`25`,``, , ,``,``, ,``, ,``),
(`26`,``, , ,``,``, ,``, ,``),
(`27`,``, , ,``,``, ,``, ,``),
(`28`,``, , ,``,``, ,``, ,``),
(`29`,``, , ,``,``, ,``, ,``),
(`30`,``, , ,``,``, ,``, ,``),
(`31`,``, , ,``,``, ,``, ,``),
(`32`,``, , ,``,``, ,``, ,``),
(`33`,``, , ,``,``, ,``, ,``),
(`34`,``, , ,``,``, ,``, ,``),
(`35`,``, , ,``,``, ,``, ,``),
(`36`,``, , ,``,``, ,``, ,``),
(`37`,``, , ,``,``, ,``, ,``),
(`38`,``, , ,``,``, ,``, ,``),
(`39`,``, , ,``,``, ,``, ,``),
(`40`,``, , ,``,``, ,``, ,``),
(`41`,``, , ,``,``, ,``, ,``),
(`42`,``, , ,``,``, ,``, ,``),
(`43`,``, , ,``,``, ,``, ,``),
(`44`,``, , ,``,``, ,``, ,``),
(`45`,``, , ,``,``, ,``, ,``),
(`46`,``, , ,``,``, ,``, ,``),
(`47`,``, , ,``,``, ,``, ,``),
(`48`,``, , ,``,``, ,``, ,``),
(`49`,``, , ,``,``, ,``, ,``),
(`50`,``, , ,``,``, ,``, ,``),
(`51`,``, , ,``,``, ,``, ,``),
(`52`,``, , ,``,``, ,``, ,``),
(`53`,``, , ,``,``, ,``, ,``),
(`54`,``, , ,``,``, ,``, ,``),
(`55`,``, , ,``,``, ,``, ,``),
(`56`,``, , ,``,``, ,``, ,``),
(`57`,``, , ,``,``, ,``, ,``),
(`58`,``, , ,``,``, ,``, ,``);


DROP TABLE IF EXISTS `user`;

CREATE TABLE `user`(
	`uid` varchar(50) NOT NULL,
	`username` varchar(20) DEFAULT NULL,
	`password` varchar(20) DEFAULT NULL,
	`truename` varchar(20) DEFAULT NULL,
	`email`    varchar(30) DEFAULT NULL,
	`telephone` varchar(20) DEFAULT NULL,
	`birthday` date DEFAULT NULL,
	`sex` varchar(10) DEFAULT NULL,
	`state` int(10) DEFAULT NULL,
	`code` varchar(50) DEFAULT NULL,
	PRIMARY KEY (`uid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
