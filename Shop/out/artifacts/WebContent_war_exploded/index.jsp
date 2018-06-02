<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

	<head >
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>宠物家园首页</title>
		<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
		<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="js/bootstrap.min.js" type="text/javascript"></script>
	</head>

	<body background="img/6.gif">

		<div class="container-fluid">

			<!-- 引入header.jsp -->
			<jsp:include page="/header.jsp"></jsp:include>

			<!-- 轮播图 -->
			<div class="container-fluid">
				<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
					<!-- 轮播图的中的小点 -->
					<ol class="carousel-indicators">
						<li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
						<li data-target="#carousel-example-generic" data-slide-to="1"></li>
						<li data-target="#carousel-example-generic" data-slide-to="2"></li>
					</ol>
					<!-- 轮播图的轮播图片 -->
					<div class="carousel-inner" role="listbox">
						<div class="item active">
							<img src="picture/1.jpg" >
							<div class="carousel-caption">
								<!-- 轮播图上的文字 -->
							</div>
						</div>
						<div class="item">
							<img src="picture/2.jpg">
							<div class="carousel-caption">
								<!-- 轮播图上的文字 -->
							</div>
						</div>
						<div class="item">
							<img src="picture/3.jpg">
							<div class="carousel-caption">
								<!-- 轮播图上的文字 -->
							</div>
						</div>
					</div>

					<!-- 上一张 下一张按钮 -->
					<a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
						<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
						<span class="sr-only">Previous</span>
					</a>
					<a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
						<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
						<span class="sr-only">Next</span>
					</a>
				</div>
			</div>
			
			<!-- 热门商品 -->
			<div class="container-fluid">
				<div class="col-md-12">
					<h2>热门宠物&nbsp;&nbsp;<img src="img/2.jpg" width="300" height="300"/></h2>
				</div>
				<div class="col-md-2" style="border:1px solid #E7E7E7;border-right:0;padding:0;">
					<img src="cat/橘猫.jpg" width="205" height="200" style="display: inline-block;"/>
					<p><a href="#" style='color:#666'>橘猫</a></p>
				</div>
				<div class="col-md-10">
					<div class="col-md-6" style="text-align:center;height:200px;padding:0px;">
						<a href="#">
							<img src="dog/柴犬.jpg" width="260px" height="180px" style="display: inline-block;">
							<p><a href="#" style='color:#666'>柴犬</a></p>
						</a>
					</div>

					<!-- jstl表达式，el表达式-->
					<c:forEach items="${hotProductList}" var="hotPro">
						<div class="col-md-2" style="text-align:center;height:200px;padding:10px 0px;">
							<a href="${pageContext.request.contextPath}/product?method=productInfo&pid=${hotPro.pid}">
								<img src="${pageContext.request.contextPath}/${hotPro.pimage}" width="130" height="130" style="display: inline-block;">
							</a>
							<p><a href="product_info.html" style='color:#666'>${hotPro.pname}</a></p>
							<p><font color="#E4393C" style="font-size:16px">&yen;${hotPro.shop_price}</font></p>
						</div>
					</c:forEach>

				</div>
			</div>
			
			<!-- 广告条 -->
            <div class="container-fluid">
				<img src="img/8.gif" width="100%" height="100"/>
			</div>
			
			<!-- 最新商品 -->
			<div class="container-fluid">
				<div class="col-md-12">
					<h2>最新宠物&nbsp;&nbsp;
						<a><img src="dog/柯基.jpg" width="230px" height="150px"/>柯基</a>
						<a><img src="dog/哈士奇.jpg" width="230px" height="150px">哈士奇</a>
					</h2>
				</div>
				<!--div class="col-md-5 pull-right"  style="border:1px solid #E7E7E7;border-right:0;padding:0;">

					<a href="product_info.htm">
							<img src="cat/布偶猫.jpg" width="200px" height="150px" style="display: inline-block;">
						<p><a href="product_info.html" style='color:#666'>布偶猫</a></p>
					</a>
						<img src="dog/拉布拉多.jpg" width="200px" height="150px" style="display: inline-block;"/>

					<p><a href="product_info.html" style='color:#666'>拉布拉多</a></p>

				</div-->


				<div class="col-md-2 pull-right" style="text-align:center;height:200px;padding:10px 0px;">
						<a href="#">
							<img src="dog/拉布拉多.jpg" width="130" height="130" style="display: inline-block;">
						</a>
						<p><a href="#" style='color:#666'>拉布拉多</a></p>
						<p><font color="#E4393C" style="font-size:16px">&yen1800</font></p>
				</div>

				<div class="col-md-10">

					<!-- jstl标签，el表达式-->
					<c:forEach items="${newProductList}" var="newPro">
						<div class="col-md-2" style="text-align:center;height:200px;padding:10px 0px;">
							<a href="${pageContext.request.contextPath}/product?method=productInfo&pid=${newPro.pid}">
								<img src="${pageContext.request.contextPath}/${newPro.pimage}" width="130" height="130" style="display: inline-block;">
							</a>
							<p><a href="product_info.html" style='color:#666'>${newPro.pname}</a></p>
							<p><font color="#E4393C" style="font-size:16px">&yen${newPro.shop_price}</font></p>
						</div>
					</c:forEach>

				</div>
			</div>


			<img src="img/分隔.jpg" >
			<h1><span class="label label-default">爱宠分享</span></h1>
			<div class="media">
				<a class="media-left" href="#">
					<img class="media-object" src="cat/橘猫.jpg" alt="媒体对象">
				</a>
				<div class="media-body text-muted">
					<h4 class="media-heading text-danger"><b>橘猫</b></h4>
					<b>橘猫是家猫常见的一种毛色，普遍存在于混种猫和不具独特规定毛色的注册纯种猫中，与任何品种无关，只与被毛遗传基因有关。
					橘猫的毛色通常分成全橘色、橘白相间两种。全橘色的猫，身上会带有浅浅白色条纹相间，仅有少数猫会在肚子呈现白色。橘白
					相间的猫，身上有大块橘色斑块与白底毛色相间。
					</b>
				</div>
			</div>

			<div class="media text-muted">
				<a class="media-left" href="#">
					<img class="media-object" src="dog/阿拉斯加.jpg" alt="媒体对象">
				</a>
				<div class="media-body">
					<h4 class="media-heading text-danger"><b>阿拉斯加</b></h4>
					<b>阿拉斯加雪橇犬是最古老的雪橇犬之一。它的名字来自爱斯基摩人的伊努伊特族的一个叫做马拉缪特的部落，
					这个部落生活在阿拉斯加西部一个叫做扣赞伯(Kotzebue)的岸边。在阿拉斯加成为美国领土的一部分之前，
					这一地区叫做Alashak或是Alyeska，翻译出来就是“广阔的土地”，这是发现这一地区的俄国人给它取的名字。
					这种犬与同在阿拉斯加的其它犬种不同，四肢强壮有力，培育它的目的是为了耐力而不是速度，因而它们的主要用途是拉雪橇。
					</b>
				</div>
			</div>

			<a href="${pageContext.request.contextPath}/findAllDiscuss"><h1><span class="label label-default">更多分享</span></h1></a>


			
			<!--包含其他页面， 引入footer.jsp -->
			<jsp:include page="/footer.jsp"></jsp:include>
			
		</div>
	</body>

</html>