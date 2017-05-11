[#ftl]
<!-- BEGIN DASHBOARD STATS -->
<div class="row">
	<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
		<div class="dashboard-stat blue">
			<div class="visual">
				<i class="fa fa-comments"></i>
			</div>
			<div class="details">
				<div class="number">
					1349
				</div>
				<div class="desc">                           
					New Feedbacks
				</div>
			</div>
			<a class="more" href="#">
			View more <i class="m-icon-swapright m-icon-white"></i>
			</a>                 
		</div>
	</div>
	<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
		<div class="dashboard-stat green">
			<div class="visual">
				<i class="fa fa-shopping-cart"></i>
			</div>
			<div class="details">
				<div class="number">549</div>
				<div class="desc">New Orders</div>
			</div>
			<a class="more" href="#">
			View more <i class="m-icon-swapright m-icon-white"></i>
			</a>                 
		</div>
	</div>
	<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
		<div class="dashboard-stat purple">
			<div class="visual">
				<i class="fa fa-globe"></i>
			</div>
			<div class="details">
				<div class="number">+89%</div>
				<div class="desc">Brand Popularity</div>
			</div>
			<a class="more" href="#">
			View more <i class="m-icon-swapright m-icon-white"></i>
			</a>                 
		</div>
	</div>
	<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
		<div class="dashboard-stat yellow">
			<div class="visual">
				<i class="fa fa-bar-chart-o"></i>
			</div>
			<div class="details">
				<div class="number">12,5M$</div>
				<div class="desc">Total Profit</div>
			</div>
			<a class="more" href="#">
			View more <i class="m-icon-swapright m-icon-white"></i>
			</a>                 
		</div>
	</div>
</div>
<div class="clearfix"></div>
<!-- END DASHBOARD STATS -->

<script src="${base}/static/echarts-2.0.4/www2/js/echarts-plain-map.js"></script>
<div class="row">
	<div class="col-md-6 col-sm-6">
		<!-- BEGIN PORTLET-->
		<div class="portlet solid bordered light-grey">
			<div class="portlet-title">
				<div class="caption"><i class="fa fa-bar-chart-o"></i>柱状图</div>
				
			</div>
			<div class="portlet-body">
				<div id="site_statistics_content">
					<div id="myChart211" style="height:240px;border:1px solid #ccc;padding:1px;"></div>
					
					<script type="text/javascript">
						var myChart = echarts.init(document.getElementById('myChart211'));
					    myChart.setOption({
					        tooltip : {
					            trigger: 'axis'
					        },
					        grid : {
					        	x: 30,
					        	y: 30,
					        	x2:20,
					        	y2:30,
					        	width:430
					        },
					        legend: {
					            data:['蒸发量','降水量']
					        },
					        toolbox: {
					            show : true,
					            feature : {
					                //mark : {show: true},
					                //dataView : {show: true, readOnly: false},
					                magicType : {show: true, type: ['line', 'bar']},
					                //restore : {show: true},
					                saveAsImage : {show: true}
					            }
					        },
					        calculable : false,
					        xAxis : [
					            {
					                type : 'category',
					                axisLabel : {
					                	rotate: 45   //文字旋转45度排列
					                },
					                data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
					            }
					        ],
					        yAxis : [
					            {
					                type : 'value',
					                splitArea : {show : true}
					            }
					        ],
					        series : [
					            {
					                name:'蒸发量',
					                type:'bar',
					                data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
					            },
					            {
					                name:'降水量',
					                type:'bar',
					                data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3]
					            }
					        ]
					    });
					</script>
				</div>
			</div>
		</div>
		<!-- END PORTLET-->
	</div>
	<div class="col-md-6 col-sm-6">
		<!-- BEGIN PORTLET-->
		<div class="portlet solid light-grey bordered">
			<div class="portlet-title">
				<div class="caption"><i class="fa fa-bullhorn"></i>饼图</div>
			</div>
			<div class="portlet-body">
				<div id="site_activities_loading">
				</div>
				<div id="site_activities_content">
					<div id="myChart212" style="height:240px;border:1px solid #ccc;padding:1px;"></div>
					
					<script type="text/javascript">
						var myChart2 = echarts.init(document.getElementById('myChart212'));
					    myChart2.setOption({
					        title : {
						        text: '某站点用户访问来源',
						        subtext: '纯属虚构',
						        x:'center'
						    },
						    tooltip : {
						        trigger: 'item',
						        formatter: "{a} <br/>{b} : {c} ({d}%)"
						    },
						    legend: {
						        orient : 'vertical',
						        x : 'left',
						        data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
						    },
						    toolbox: {
						        show : true,
						        feature : {
						            //mark : {show: true},
						            //dataView : {show: true, readOnly: false},
						            //restore : {show: true},
						            saveAsImage : {show: true}
						        }
						    },
						    calculable : false,
						    series : [
						        {
						            name:'访问来源',
						            type:'pie',
						            radius : '55%',
						            center: ['50%', '60%'],
						            data:[
						                {value:335, name:'直接访问'},
						                {value:310, name:'邮件营销'},
						                {value:234, name:'联盟广告'},
						                {value:135, name:'视频广告'},
						                {value:1548, name:'搜索引擎'}
						            ]
						        }
						    ]
					    });
					</script>
				</div>
			</div>
		</div>
		<!-- END PORTLET-->
	</div>
</div>

<div class="row">
	<div class="col-md-6 col-sm-6">
		<!-- BEGIN PORTLET-->
		<div class="portlet solid bordered light-grey">
			<div class="portlet-title">
				<div class="caption"><i class="fa fa-bar-chart-o"></i>环形图</div>
				
			</div>
			<div class="portlet-body">
				<div id="site_statistics_content">
					<div id="myChart213" style="height:240px;border:1px solid #ccc;padding:1px;"></div>
					
					<script type="text/javascript">
						var myChart = echarts.init(document.getElementById('myChart213'));
						var labelTop = {
						    normal : {
						        label : {
						            show : true,
						            position : 'center',
						            textStyle: {
						                baseline : 'bottom'
						            }
						        },
						        labelLine : {
						            show : false
						        }
						    }
						};
						var labelBottom = {
						    normal : {
						        color: '#ccc',
						        label : {
						            show : true,
						            position : 'center',
						            formatter : function (a,b,c){return 100 - c + '%'},
						            textStyle: {
						                baseline : 'top'
						            }
						        },
						        labelLine : {
						            show : false
						        }
						    },
						    emphasis: {
						        color: 'rgba(0,0,0,0)'
						    }
						};
						var radius = [50, 70];
					    myChart.setOption({
					        legend: {
						        x : 'center',
						        y : 'center',
						        data:[
						            //'GoogleMaps'
						        ]
						    },
						    title : {
						        text: 'The App World',
						        subtext: 'from global web index',
						        x: 'center'
						    },
						    toolbox: {
						        show : true,
						        feature : {
						            //dataView : {show: true, readOnly: false},
						            //restore : {show: true},
						            saveAsImage : {show: true}
						        }
						    },
						    series : [
						        {
						            type : 'pie',
						            //center : ['10%', '30%'],
						            radius : radius,
						            data : [
						                {name:'other', value:30, itemStyle : labelBottom},
						                {name:'GoogleMaps', value:70,itemStyle : labelTop}
						            ]
						        }
						    ]
					    });
					</script>
				</div>
			</div>
		</div>
		<!-- END PORTLET-->
	</div>
	<div class="col-md-6 col-sm-6">
		<!-- BEGIN PORTLET-->
		<div class="portlet solid light-grey bordered">
			<div class="portlet-title">
				<div class="caption"><i class="fa fa-bullhorn"></i>线形图</div>
			</div>
			<div class="portlet-body">
				<div id="site_activities_loading">
				</div>
				<div id="site_activities_content">
					<div id="myChart214" style="height:240px;border:1px solid #ccc;padding:1px;"></div>
					
					<script type="text/javascript">
						var myChart = echarts.init(document.getElementById('myChart214'));
					    myChart.setOption({
					        tooltip : {
					            trigger: 'axis'
					        },
					        grid : {
					        	x: 30,
					        	y: 30,
					        	x2:20,
					        	y2:30,
					        	width:430
					        },
					        legend: {
					            data:['蒸发量','降水量']
					        },
					        toolbox: {
					            show : true,
					            feature : {
					                //mark : {show: true},
					                //dataView : {show: true, readOnly: false},
					                magicType : {show: true, type: ['line', 'bar']},
					                //restore : {show: true},
					                saveAsImage : {show: true}
					            }
					        },
					        calculable : false,
					        xAxis : [
					            {
					                type : 'category',
					                data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
					            }
					        ],
					        yAxis : [
					            {
					                type : 'value',
					                splitArea : {show : true}
					            }
					        ],
					        series : [
					            {
					                name:'蒸发量',
					                type:'line',
					                data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
					            },
					            {
					                name:'降水量',
					                type:'line',
					                data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3]
					            }
					        ]
					    });
					</script>
				</div>
			</div>
		</div>
		<!-- END PORTLET-->
	</div>
</div>

<div class="row">
	<div class="col-md-6 col-sm-6">
		<!-- BEGIN PORTLET-->
		<div class="portlet solid bordered light-grey">
			<div class="portlet-title">
				<div class="caption"><i class="fa fa-bar-chart-o"></i>标尺/进度</div>
				
			</div>
			<div class="portlet-body">
				<div id="site_statistics_content">
					<link href="${base}/static/metronic1.5.4/assets/plugins/ion.rangeslider/css/ion.rangeSlider.css" rel="stylesheet" type="text/css"/>
					<link href="${base}/static/metronic1.5.4/assets/plugins/ion.rangeslider/css/ion.rangeSlider.Metronic.css" rel="stylesheet" type="text/css"/>
					<script src="${base}/static/metronic1.5.4/assets/plugins/ion.rangeslider/js/ion-rangeSlider/ion.rangeSlider.min.js"></script>
					
					<input id="range_5" type="text" name="range_5" value="" />
					<script>
						$("#range_5").ionRangeSlider({
			                min: 0,
			                max: 10,
			                from: 0,
			                to: 6,
			                type: 'double',
			                step: 0.1,
			                postfix: " mm",
			                prettify: false,
			                hasGrid: true
			            });
					</script>
					
					
				</div>
			</div>
		</div>
		<!-- END PORTLET-->
	</div>
	<div class="col-md-6 col-sm-6">
		<!-- BEGIN PORTLET-->
		<div class="portlet solid light-grey bordered">
			<div class="portlet-title">
				<div class="caption"><i class="fa fa-bullhorn"></i>仪表板</div>
			</div>
			<div class="portlet-body">
				<div id="site_activities_loading">
				</div>
				<div id="site_activities_content">
					<div id="myChart216" style="height:240px;border:1px solid #ccc;padding:1px;"></div>
					
					<script type="text/javascript">
						var myChart = echarts.init(document.getElementById('myChart216'));
					    myChart.setOption({
					        tooltip : {
						        formatter: "{a} <br/>{b} : {c}%"
						    },
						    toolbox: {
						        show : true,
						        feature : {
						            //mark : {show: true},
						            //restore : {show: true},
						            saveAsImage : {show: true}
						        }
						    },
						    series : [
						        {
						            name:'业务指标',
						            type:'gauge',
						            startAngle: 180,
						            endAngle: 0,
						            center : ['50%', '100%'],    // 默认全局居中
						            radius : 200,
						            axisLine: {            // 坐标轴线
						                lineStyle: {       // 属性lineStyle控制线条样式
						                    width: 150
						                }
						            },
						            axisTick: {            // 坐标轴小标记
						                splitNumber: 10,   // 每份split细分多少段
						                length :12,        // 属性length控制线长
						            },
						            axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
						                formatter: function(v){
						                    switch (v+''){
						                        case '10': return '低';
						                        case '50': return '中';
						                        case '90': return '高';
						                        default: return '';
						                    }
						                },
						                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
						                    color: '#fff',
						                    fontSize: 15,
						                    fontWeight: 'bolder'
						                }
						            },
						            pointer: {
						                width:30,
						                length: '90%',
						                color: 'rgba(255, 255, 255, 0.8)'
						            },
						            title : {
						                show : true,
						                offsetCenter: [0, '-60%'],       // x, y，单位px
						                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
						                    color: '#fff',
						                    fontSize: 20
						                }
						            },
						            detail : {
						                show : true,
						                backgroundColor: 'rgba(0,0,0,0)',
						                borderWidth: 0,
						                borderColor: '#ccc',
						                width: 20,
						                height: 20,
						                offsetCenter: [0, -20],       // x, y，单位px
						                formatter:'{value}%',
						                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
						                    fontSize : 20
						                }
						            },
						            data:[{value: 70, name: '完成率'}]
						        }
						    ]
					    });
					</script>
				</div>
			</div>
		</div>
		<!-- END PORTLET-->
	</div>
</div>

<div class="row">
	<div class="col-md-12 col-sm-12">
		<!-- BEGIN PORTLET-->
		<div class="portlet solid bordered light-grey">
			<div class="portlet-title">
				<div class="caption"><i class="fa fa-bar-chart-o"></i>力向图</div>
				
			</div>
			<div class="portlet-body">
				<div id="site_statistics_content">
					<div id="myChart217" style="height:500px;border:1px solid #ccc;padding:1px;"></div>
					
					<script type="text/javascript">
						var myChart = echarts.init(document.getElementById('myChart217'));
					    myChart.setOption({
					        title : {
						        text: '人物关系：乔布斯',
						        subtext: '数据来自人立方',
						        x:'right',
						        y:'bottom'
						    },
						    tooltip : {
						        trigger: 'item',
						        formatter: '{a} : {b}'
						    },
						    toolbox: {
						        show : true,
						        feature : {
						            //restore : {show: true},
						            //magicType: {show: true, type: ['force', 'chord']},
						            saveAsImage : {show: true}
						        }
						    },
						    legend: {
						        x: 'left',
						        data:['家人','朋友']
						    },
						    series : [
						        {
						            type:'force',
						            name : "人物关系",
						            ribbonType: false,
						            categories : [
						                {
						                    name: '人物'
						                },
						                {
						                    name: '家人',
						                    symbol: 'diamond'
						                },
						                {
						                    name:'朋友'
						                }
						            ],
						            itemStyle: {
						                normal: {
						                    label: {
						                        show: true,
						                        textStyle: {
						                            color: '#333'
						                        }
						                    },
						                    nodeStyle : {
						                        brushType : 'both',
						                        borderColor : 'rgba(255,215,0,0.4)',
						                        borderWidth : 1
						                    }
						                },
						                emphasis: {
						                    label: {
						                        show: false
						                        // textStyle: null      // 默认使用全局文本样式，详见TEXTSTYLE
						                    },
						                    nodeStyle : {
						                        //r: 30
						                    },
						                    linkStyle : {}
						                }
						            },
						            minRadius : 15,
						            maxRadius : 25,
						            gravity: 1.1,
						            scaling: 1.2,
						            draggable: false,
						            linkSymbol: 'arrow',
						            steps: 10,
						            coolDown: 0.9,
						            //preventOverlap: true,
						            nodes:[
						                {
						                    category:0, name: '乔布斯', value : 10,
						                    //symbol: 'image://http://www.damndigital.com/wp-content/uploads/2010/12/steve-jobs.jpg',
						                    symbolSize: 30,
						                    draggable: true,
						                    itemStyle: {
						                        normal: {
						                            label: {
						                                position: 'right',
						                                textStyle: {
						                                    color: 'black'
						                                }
						                            }
						                        }
						                    }
						                },
						                {category:1, name: '丽萨-乔布斯',value : 2},
						                {category:1, name: '保罗-乔布斯',value : 3},
						                {category:1, name: '克拉拉-乔布斯',value : 3},
						                {category:1, name: '劳伦-鲍威尔',value : 7},
						                {category:2, name: '史蒂夫-沃兹尼艾克',value : 5},
						                {category:2, name: '奥巴马',value : 8},
						                {category:2, name: '比尔-盖茨',value : 9},
						                {category:2, name: '乔纳森-艾夫',value : 4},
						                {category:2, name: '蒂姆-库克',value : 4},
						                //{category:2, name: '龙-韦恩',value : 1},
						            ],
						            links : [
						                {source : '丽萨-乔布斯', target : '乔布斯', weight : 1, name: '女儿'},
						                {source : '保罗-乔布斯', target : '乔布斯', weight : 2, name: '父亲'},
						                {source : '克拉拉-乔布斯', target : '乔布斯', weight : 1, name: '母亲'},
						                {source : '劳伦-鲍威尔', target : '乔布斯', weight : 2},
						                {source : '史蒂夫-沃兹尼艾克', target : '乔布斯', weight : 3, name: '合伙人'},
						                {source : '奥巴马', target : '乔布斯', weight : 1},
						                {source : '比尔-盖茨', target : '乔布斯', weight : 6, name: '竞争对手'},
						                {source : '乔纳森-艾夫', target : '乔布斯', weight : 1, name: '爱将'},
						                //{source : '蒂姆-库克', target : '乔布斯', weight : 1},
						                //{source : '龙-韦恩', target : '乔布斯', weight : 1},
						                //{source : '克拉拉-乔布斯', target : '保罗-乔布斯', weight : 1},
						                //{source : '奥巴马', target : '保罗-乔布斯', weight : 1},
						                //{source : '奥巴马', target : '克拉拉-乔布斯', weight : 1},
						                //{source : '奥巴马', target : '劳伦-鲍威尔', weight : 1},
						                //{source : '奥巴马', target : '史蒂夫-沃兹尼艾克', weight : 1},
						                //{source : '比尔-盖茨', target : '奥巴马', weight : 6},
						                //{source : '比尔-盖茨', target : '克拉拉-乔布斯', weight : 1},
						                {source : '蒂姆-库克', target : '奥巴马', weight : 1}
						            ]
						        }
						    ]
					    });
					</script>
				</div>
			</div>
		</div>
		<!-- END PORTLET-->
	</div>
	
</div>

[#--
<div class="clearfix"></div>
<div class="row ">
	<div class="col-md-6 col-sm-6">
		<div class="portlet box blue">
			<div class="portlet-title">
				<div class="caption"><i class="fa fa-bell-o"></i>Recent Activities</div>
				<div class="actions">
					<div class="btn-group">
						<a class="btn btn-sm default" href="#" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
						Filter By
						<i class="fa fa-angle-down"></i>
						</a>
						<div class="dropdown-menu hold-on-click dropdown-checkboxes pull-right">
							<label><input type="checkbox" /> Finance</label>
							<label><input type="checkbox" checked="" /> Membership</label>
							<label><input type="checkbox" /> Customer Support</label>
							<label><input type="checkbox" checked="" /> HR</label>
							<label><input type="checkbox" /> System</label>
						</div>
					</div>
				</div>
			</div>
			<div class="portlet-body">
				<div class="scroller" style="height: 300px;" data-always-visible="1" data-rail-visible="0">
					<ul class="feeds">
						<li>
							<div class="col1">
								<div class="cont">
									<div class="cont-col1">
										<div class="label label-sm label-info">                        
											<i class="fa fa-check"></i>
										</div>
									</div>
									<div class="cont-col2">
										<div class="desc">
											You have 4 pending tasks.
											<span class="label label-sm label-warning ">
											Take action 
											<i class="fa fa-share"></i>
											</span>
										</div>
									</div>
								</div>
							</div>
							<div class="col2">
								<div class="date">
									Just now
								</div>
							</div>
						</li>
						<li>
							<a href="#">
								<div class="col1">
									<div class="cont">
										<div class="cont-col1">
											<div class="label label-sm label-success">                        
												<i class="fa fa-bar-chart-o"></i>
											</div>
										</div>
										<div class="cont-col2">
											<div class="desc">
												Finance Report for year 2013 has been released.   
											</div>
										</div>
									</div>
								</div>
								<div class="col2">
									<div class="date">
										20 mins
									</div>
								</div>
							</a>
						</li>
						<li>
							<div class="col1">
								<div class="cont">
									<div class="cont-col1">
										<div class="label label-sm label-danger">                      
											<i class="fa fa-user"></i>
										</div>
									</div>
									<div class="cont-col2">
										<div class="desc">
											You have 5 pending membership that requires a quick review.                       
										</div>
									</div>
								</div>
							</div>
							<div class="col2">
								<div class="date">
									24 mins
								</div>
							</div>
						</li>
						<li>
							<div class="col1">
								<div class="cont">
									<div class="cont-col1">
										<div class="label label-sm label-info">                        
											<i class="fa fa-shopping-cart"></i>
										</div>
									</div>
									<div class="cont-col2">
										<div class="desc">
											New order received with <span class="label label-sm label-success">Reference Number: DR23923</span>             
										</div>
									</div>
								</div>
							</div>
							<div class="col2">
								<div class="date">
									30 mins
								</div>
							</div>
						</li>
						<li>
							<div class="col1">
								<div class="cont">
									<div class="cont-col1">
										<div class="label label-sm label-success">                      
											<i class="fa fa-user"></i>
										</div>
									</div>
									<div class="cont-col2">
										<div class="desc">
											You have 5 pending membership that requires a quick review.                       
										</div>
									</div>
								</div>
							</div>
							<div class="col2">
								<div class="date">
									24 mins
								</div>
							</div>
						</li>
						<li>
							<div class="col1">
								<div class="cont">
									<div class="cont-col1">
										<div class="label label-sm label-default">                        
											<i class="fa fa-bell-o"></i>
										</div>
									</div>
									<div class="cont-col2">
										<div class="desc">
											Web server hardware needs to be upgraded. 
											<span class="label label-sm label-default ">Overdue</span>             
										</div>
									</div>
								</div>
							</div>
							<div class="col2">
								<div class="date">
									2 hours
								</div>
							</div>
						</li>
						<li>
							<a href="#">
								<div class="col1">
									<div class="cont">
										<div class="cont-col1">
											<div class="label label-sm label-default">                        
												<i class="fa fa-briefcase"></i>
											</div>
										</div>
										<div class="cont-col2">
											<div class="desc">
												IPO Report for year 2013 has been released.   
											</div>
										</div>
									</div>
								</div>
								<div class="col2">
									<div class="date">
										20 mins
									</div>
								</div>
							</a>
						</li>
						<li>
							<div class="col1">
								<div class="cont">
									<div class="cont-col1">
										<div class="label label-sm label-info">                        
											<i class="fa fa-check"></i>
										</div>
									</div>
									<div class="cont-col2">
										<div class="desc">
											You have 4 pending tasks.
											<span class="label label-sm label-warning ">
											Take action 
											<i class="fa fa-share"></i>
											</span>
										</div>
									</div>
								</div>
							</div>
							<div class="col2">
								<div class="date">
									Just now
								</div>
							</div>
						</li>
						<li>
							<a href="#">
								<div class="col1">
									<div class="cont">
										<div class="cont-col1">
											<div class="label label-sm label-danger">                        
												<i class="fa fa-bar-chart-o"></i>
											</div>
										</div>
										<div class="cont-col2">
											<div class="desc">
												Finance Report for year 2013 has been released.   
											</div>
										</div>
									</div>
								</div>
								<div class="col2">
									<div class="date">
										20 mins
									</div>
								</div>
							</a>
						</li>
						<li>
							<div class="col1">
								<div class="cont">
									<div class="cont-col1">
										<div class="label label-sm label-default">                      
											<i class="fa fa-user"></i>
										</div>
									</div>
									<div class="cont-col2">
										<div class="desc">
											You have 5 pending membership that requires a quick review.                       
										</div>
									</div>
								</div>
							</div>
							<div class="col2">
								<div class="date">
									24 mins
								</div>
							</div>
						</li>
						<li>
							<div class="col1">
								<div class="cont">
									<div class="cont-col1">
										<div class="label label-sm label-info">                        
											<i class="fa fa-shopping-cart"></i>
										</div>
									</div>
									<div class="cont-col2">
										<div class="desc">
											New order received with <span class="label label-sm label-success">Reference Number: DR23923</span>             
										</div>
									</div>
								</div>
							</div>
							<div class="col2">
								<div class="date">
									30 mins
								</div>
							</div>
						</li>
						<li>
							<div class="col1">
								<div class="cont">
									<div class="cont-col1">
										<div class="label label-sm label-success">                      
											<i class="fa fa-user"></i>
										</div>
									</div>
									<div class="cont-col2">
										<div class="desc">
											You have 5 pending membership that requires a quick review.                       
										</div>
									</div>
								</div>
							</div>
							<div class="col2">
								<div class="date">
									24 mins
								</div>
							</div>
						</li>
						<li>
							<div class="col1">
								<div class="cont">
									<div class="cont-col1">
										<div class="label label-sm label-warning">                        
											<i class="fa fa-bell-o"></i>
										</div>
									</div>
									<div class="cont-col2">
										<div class="desc">
											Web server hardware needs to be upgraded. 
											<span class="label label-sm label-default ">Overdue</span>             
										</div>
									</div>
								</div>
							</div>
							<div class="col2">
								<div class="date">
									2 hours
								</div>
							</div>
						</li>
						<li>
							<a href="#">
								<div class="col1">
									<div class="cont">
										<div class="cont-col1">
											<div class="label label-sm label-info">                        
												<i class="fa fa-briefcase"></i>
											</div>
										</div>
										<div class="cont-col2">
											<div class="desc">
												IPO Report for year 2013 has been released.   
											</div>
										</div>
									</div>
								</div>
								<div class="col2">
									<div class="date">
										20 mins
									</div>
								</div>
							</a>
						</li>
					</ul>
				</div>
				<div class="scroller-footer">
					<div class="pull-right">
						<a href="#">See All Records <i class="m-icon-swapright m-icon-gray"></i></a> &nbsp;
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-6 col-sm-6">
		<div class="portlet box green tasks-widget">
			<div class="portlet-title">
				<div class="caption"><i class="fa fa-check"></i>Tasks</div>
				<div class="tools">
					<a href="#portlet-config" data-toggle="modal" class="config"></a>
					<a href="" class="reload"></a>
				</div>
				<div class="actions">
					<div class="btn-group">
						<a class="btn default btn-xs" href="#" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
						More
						<i class="fa fa-angle-down"></i>
						</a>
						<ul class="dropdown-menu pull-right">
							<li><a href="#"><i class="i"></i> All Project</a></li>
							<li class="divider"></li>
							<li><a href="#">AirAsia</a></li>
							<li><a href="#">Cruise</a></li>
							<li><a href="#">HSBC</a></li>
							<li class="divider"></li>
							<li><a href="#">Pending <span class="badge badge-important">4</span></a></li>
							<li><a href="#">Completed <span class="badge badge-success">12</span></a></li>
							<li><a href="#">Overdue <span class="badge badge-warning">9</span></a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="portlet-body">
				<div class="task-content">
					<div class="scroller" style="height: 305px;" data-always-visible="1" data-rail-visible1="1">
						<!-- START TASK LIST -->
						<ul class="task-list">
							<li>
								<div class="task-checkbox">
									<input type="checkbox" class="liChild" value=""  />                                       
								</div>
								<div class="task-title">
									<span class="task-title-sp">Present 2013 Year IPO Statistics at Board Meeting</span>
									<span class="label label-sm label-success">Company</span>
									<span class="task-bell"><i class="fa fa-bell-o"></i></span>
								</div>
								<div class="task-config">
									<div class="task-config-btn btn-group">
										<a class="btn btn-xs default" href="#" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
										<i class="fa fa-cog"></i><i class="fa fa-angle-down"></i></a>
										<ul class="dropdown-menu pull-right">
											<li><a href="#"><i class="fa fa-check"></i> Complete</a></li>
											<li><a href="#"><i class="fa fa-pencil"></i> Edit</a></li>
											<li><a href="#"><i class="fa fa-trash-o"></i> Cancel</a></li>
										</ul>
									</div>
								</div>
							</li>
							<li>
								<div class="task-checkbox">
									<input type="checkbox" class="liChild" value=""/>                                       
								</div>
								<div class="task-title">
									<span class="task-title-sp">Hold An Interview for Marketing Manager Position</span>
									<span class="label label-sm label-danger">Marketing</span>
								</div>
								<div class="task-config">
									<div class="task-config-btn btn-group">
										<a class="btn btn-xs default" href="#" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
										<i class="fa fa-cog"></i><i class="fa fa-angle-down"></i></a>
										<ul class="dropdown-menu pull-right">
											<li><a href="#"><i class="fa fa-check"></i> Complete</a></li>
											<li><a href="#"><i class="fa fa-pencil"></i> Edit</a></li>
											<li><a href="#"><i class="fa fa-trash-o"></i> Cancel</a></li>
										</ul>
									</div>
								</div>
							</li>
							<li>
								<div class="task-checkbox">
									<input type="checkbox" class="liChild" value=""/>                                       
								</div>
								<div class="task-title">
									<span class="task-title-sp">AirAsia Intranet System Project Internal Meeting</span>
									<span class="label label-sm label-success">AirAsia</span>
									<span class="task-bell"><i class="fa fa-bell-o"></i></span>
								</div>
								<div class="task-config">
									<div class="task-config-btn btn-group">
										<a class="btn btn-xs default" href="#" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
										<i class="fa fa-cog"></i><i class="fa fa-angle-down"></i></a>
										<ul class="dropdown-menu pull-right">
											<li><a href="#"><i class="fa fa-check"></i> Complete</a></li>
											<li><a href="#"><i class="fa fa-pencil"></i> Edit</a></li>
											<li><a href="#"><i class="fa fa-trash-o"></i> Cancel</a></li>
										</ul>
									</div>
								</div>
							</li>
							<li>
								<div class="task-checkbox">
									<input type="checkbox" class="liChild" value=""  />                                       
								</div>
								<div class="task-title">
									<span class="task-title-sp">Technical Management Meeting</span>
									<span class="label label-sm label-warning">Company</span>
								</div>
								<div class="task-config">
									<div class="task-config-btn btn-group">
										<a class="btn btn-xs default" href="#" data-toggle="dropdown" data-hover="dropdown" data-close-others="true"><i class="fa fa-cog"></i><i class="fa fa-angle-down"></i></a>
										<ul class="dropdown-menu pull-right">
											<li><a href="#"><i class="fa fa-check"></i> Complete</a></li>
											<li><a href="#"><i class="fa fa-pencil"></i> Edit</a></li>
											<li><a href="#"><i class="fa fa-trash-o"></i> Cancel</a></li>
										</ul>
									</div>
								</div>
							</li>
							<li>
								<div class="task-checkbox">
									<input type="checkbox" class="liChild" value=""  />                                       
								</div>
								<div class="task-title">
									<span class="task-title-sp">Kick-off Company CRM Mobile App Development</span>
									<span class="label label-sm label-info">Internal Products</span>
								</div>
								<div class="task-config">
									<div class="task-config-btn btn-group">
										<a class="btn btn-xs default" href="#" data-toggle="dropdown" data-hover="dropdown" data-close-others="true"><i class="fa fa-cog"></i><i class="fa fa-angle-down"></i></a>
										<ul class="dropdown-menu pull-right">
											<li><a href="#"><i class="fa fa-check"></i> Complete</a></li>
											<li><a href="#"><i class="fa fa-pencil"></i> Edit</a></li>
											<li><a href="#"><i class="fa fa-trash-o"></i> Cancel</a></li>
										</ul>
									</div>
								</div>
							</li>
							<li>
								<div class="task-checkbox">
									<input type="checkbox" class="liChild" value=""  />                                       
								</div>
								<div class="task-title">
									<span class="task-title-sp">
									Prepare Commercial Offer For SmartVision Website Rewamp 
									</span>
									<span class="label label-sm label-danger">SmartVision</span>
								</div>
								<div class="task-config">
									<div class="task-config-btn btn-group">
										<a class="btn btn-xs default" href="#" data-toggle="dropdown" data-hover="dropdown" data-close-others="true"><i class="fa fa-cog"></i><i class="fa fa-angle-down"></i></a>
										<ul class="dropdown-menu pull-right">
											<li><a href="#"><i class="fa fa-check"></i> Complete</a></li>
											<li><a href="#"><i class="fa fa-pencil"></i> Edit</a></li>
											<li><a href="#"><i class="fa fa-trash-o"></i> Cancel</a></li>
										</ul>
									</div>
								</div>
							</li>
							<li>
								<div class="task-checkbox">
									<input type="checkbox" class="liChild" value=""  />                                       
								</div>
								<div class="task-title">
									<span class="task-title-sp">Sign-Off The Comercial Agreement With AutoSmart</span>
									<span class="label label-sm label-default">AutoSmart</span>
									<span class="task-bell"><i class="fa fa-bell-o"></i></span>
								</div>
								<div class="task-config">
									<div class="task-config-btn btn-group">
										<a class="btn btn-xs default" href="#" data-toggle="dropdown" data-hover="dropdown" data-close-others="true"><i class="fa fa-cog"></i><i class="fa fa-angle-down"></i></a>
										<ul class="dropdown-menu pull-right">
											<li><a href="#"><i class="fa fa-check"></i> Complete</a></li>
											<li><a href="#"><i class="fa fa-pencil"></i> Edit</a></li>
											<li><a href="#"><i class="fa fa-trash-o"></i> Cancel</a></li>
										</ul>
									</div>
								</div>
							</li>
							<li>
								<div class="task-checkbox">
									<input type="checkbox" class="liChild" value=""  />                                       
								</div>
								<div class="task-title">
									<span class="task-title-sp">Company Staff Meeting</span>
									<span class="label label-sm label-success">Cruise</span>
									<span class="task-bell"><i class="fa fa-bell-o"></i></span>
								</div>
								<div class="task-config">
									<div class="task-config-btn btn-group">
										<a class="btn btn-xs default" href="#" data-toggle="dropdown" data-hover="dropdown" data-close-others="true"><i class="fa fa-cog"></i><i class="fa fa-angle-down"></i></a>
										<ul class="dropdown-menu pull-right">
											<li><a href="#"><i class="fa fa-check"></i> Complete</a></li>
											<li><a href="#"><i class="fa fa-pencil"></i> Edit</a></li>
											<li><a href="#"><i class="fa fa-trash-o"></i> Cancel</a></li>
										</ul>
									</div>
								</div>
							</li>
							<li class="last-line">
								<div class="task-checkbox">
									<input type="checkbox" class="liChild" value=""  />                                       
								</div>
								<div class="task-title">
									<span class="task-title-sp">KeenThemes Investment Discussion</span>
									<span class="label label-sm label-warning">KeenThemes</span>
								</div>
								<div class="task-config">
									<div class="task-config-btn btn-group">
										<a class="btn btn-xs default" href="#" data-toggle="dropdown" data-hover="dropdown" data-close-others="true"><i class="fa fa-cog"></i><i class="fa fa-angle-down"></i></a>
										<ul class="dropdown-menu pull-right">
											<li><a href="#"><i class="fa fa-check"></i> Complete</a></li>
											<li><a href="#"><i class="fa fa-pencil"></i> Edit</a></li>
											<li><a href="#"><i class="fa fa-trash-o"></i> Cancel</a></li>
										</ul>
									</div>
								</div>
							</li>
						</ul>
						<!-- END START TASK LIST -->
					</div>
				</div>
				<div class="task-footer">
					<span class="pull-right">
					<a href="#">See All Tasks <i class="m-icon-swapright m-icon-gray"></i></a> &nbsp;
					</span>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="clearfix"></div>
<div class="row ">
	<div class="col-md-6 col-sm-6">
		<div class="portlet box purple">
			<div class="portlet-title">
				<div class="caption"><i class="fa fa-calendar"></i>General Stats</div>
				<div class="actions">
					<a href="javascript:;" class="btn btn-sm yellow easy-pie-chart-reload"><i class="fa fa-repeat"></i> Reload</a>
				</div>
			</div>
			<div class="portlet-body">
				<div class="row">
					<div class="col-md-4">
						<div class="easy-pie-chart">
							<div class="number transactions" data-percent="55"><span>+55</span>%</div>
							<a class="title" href="#">Transactions <i class="m-icon-swapright"></i></a>
						</div>
					</div>
					<div class="margin-bottom-10 visible-sm"></div>
					<div class="col-md-4">
						<div class="easy-pie-chart">
							<div class="number visits" data-percent="85"><span>+85</span>%</div>
							<a class="title" href="#">New Visits <i class="m-icon-swapright"></i></a>
						</div>
					</div>
					<div class="margin-bottom-10 visible-sm"></div>
					<div class="col-md-4">
						<div class="easy-pie-chart">
							<div class="number bounce" data-percent="46"><span>-46</span>%</div>
							<a class="title" href="#">Bounce <i class="m-icon-swapright"></i></a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-6 col-sm-6">
		<div class="portlet box blue">
			<div class="portlet-title">
				<div class="caption"><i class="fa fa-calendar"></i>Server Stats</div>
				<div class="tools">
					<a href="" class="collapse"></a>
					<a href="#portlet-config" data-toggle="modal" class="config"></a>
					<a href="" class="reload"></a>
					<a href="" class="remove"></a>
				</div>
			</div>
			<div class="portlet-body">
				<div class="row">
					<div class="col-md-4">
						<div class="sparkline-chart">
							<div class="number" id="sparkline_bar"></div>
							<a class="title" href="#">Network <i class="m-icon-swapright"></i></a>
						</div>
					</div>
					<div class="margin-bottom-10 visible-sm"></div>
					<div class="col-md-4">
						<div class="sparkline-chart">
							<div class="number" id="sparkline_bar2"></div>
							<a class="title" href="#">CPU Load <i class="m-icon-swapright"></i></a>
						</div>
					</div>
					<div class="margin-bottom-10 visible-sm"></div>
					<div class="col-md-4">
						<div class="sparkline-chart">
							<div class="number" id="sparkline_line"></div>
							<a class="title" href="#">Load Rate <i class="m-icon-swapright"></i></a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="clearfix"></div>
<div class="row ">
	<div class="col-md-6 col-sm-6">
		<!-- BEGIN REGIONAL STATS PORTLET-->
		<div class="portlet">
			<div class="portlet-title">
				<div class="caption"><i class="fa fa-globe"></i>Regional Stats</div>
				<div class="tools">
					<a href="" class="collapse"></a>
					<a href="#portlet-config" data-toggle="modal" class="config"></a>
					<a href="" class="reload"></a>
					<a href="" class="remove"></a>
				</div>
			</div>
			<div class="portlet-body">
				<div id="region_statistics_loading">
					<img src="assets/img/loading.gif" alt="loading"/>
				</div>
				<div id="region_statistics_content" class="display-none">
					<div class="btn-toolbar margin-bottom-10">
						<div class="btn-group" data-toggle="buttons">
							<a href="" class="btn default btn-sm active">Users</a>
							<a href="" class="btn default btn-sm">Orders</a> 
						</div>
						<div class="btn-group pull-right">
							<a href="" class="btn default btn-sm dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
							Select Region <span class="fa fa-angle-down"></span>
							</a>
							<ul class="dropdown-menu pull-right">
								<li><a href="javascript:;" id="regional_stat_world">World</a></li>
								<li><a href="javascript:;" id="regional_stat_usa">USA</a></li>
								<li><a href="javascript:;" id="regional_stat_europe">Europe</a></li>
								<li><a href="javascript:;" id="regional_stat_russia">Russia</a></li>
								<li><a href="javascript:;" id="regional_stat_germany">Germany</a></li>
							</ul>
						</div>
					</div>
					<div id="vmap_world" class="vmaps display-none"></div>
					<div id="vmap_usa" class="vmaps display-none"></div>
					<div id="vmap_europe" class="vmaps display-none"></div>
					<div id="vmap_russia" class="vmaps display-none"></div>
					<div id="vmap_germany" class="vmaps display-none"></div>
				</div>
			</div>
		</div>
		<!-- END REGIONAL STATS PORTLET-->
	</div>
	<div class="col-md-6 col-sm-6">
		<!-- BEGIN PORTLET-->
		<div class="portlet paddingless">
			<div class="portlet-title line">
				<div class="caption"><i class="fa fa-bell-o"></i>Feeds</div>
				<div class="tools">
					<a href="" class="collapse"></a>
					<a href="#portlet-config" data-toggle="modal" class="config"></a>
					<a href="" class="reload"></a>
					<a href="" class="remove"></a>
				</div>
			</div>
			<div class="portlet-body">
				<!--BEGIN TABS-->
				<div class="tabbable tabbable-custom">
					<ul class="nav nav-tabs">
						<li class="active"><a href="#tab_1_1" data-toggle="tab">System</a></li>
						<li><a href="#tab_1_2" data-toggle="tab">Activities</a></li>
						<li><a href="#tab_1_3" data-toggle="tab">Recent Users</a></li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane active" id="tab_1_1">
							<div class="scroller" style="height: 290px;" data-always-visible="1" data-rail-visible="0">
								<ul class="feeds">
									<li>
										<div class="col1">
											<div class="cont">
												<div class="cont-col1">
													<div class="label label-sm label-success">                        
														<i class="fa fa-bell-o"></i>
													</div>
												</div>
												<div class="cont-col2">
													<div class="desc">
														You have 4 pending tasks.
														<span class="label label-sm label-danger ">
														Take action 
														<i class="fa fa-share"></i>
														</span>
													</div>
												</div>
											</div>
										</div>
										<div class="col2">
											<div class="date">
												Just now
											</div>
										</div>
									</li>
									<li>
										<a href="#">
											<div class="col1">
												<div class="cont">
													<div class="cont-col1">
														<div class="label label-sm label-success">                        
															<i class="fa fa-bell-o"></i>
														</div>
													</div>
													<div class="cont-col2">
														<div class="desc">
															New version v1.4 just lunched!   
														</div>
													</div>
												</div>
											</div>
											<div class="col2">
												<div class="date">
													20 mins
												</div>
											</div>
										</a>
									</li>
									<li>
										<div class="col1">
											<div class="cont">
												<div class="cont-col1">
													<div class="label label-sm label-danger">                      
														<i class="fa fa-bolt"></i>
													</div>
												</div>
												<div class="cont-col2">
													<div class="desc">
														Database server #12 overloaded. Please fix the issue.                      
													</div>
												</div>
											</div>
										</div>
										<div class="col2">
											<div class="date">
												24 mins
											</div>
										</div>
									</li>
									<li>
										<div class="col1">
											<div class="cont">
												<div class="cont-col1">
													<div class="label label-sm label-info">                        
														<i class="fa fa-bullhorn"></i>
													</div>
												</div>
												<div class="cont-col2">
													<div class="desc">
														New order received. Please take care of it.                 
													</div>
												</div>
											</div>
										</div>
										<div class="col2">
											<div class="date">
												30 mins
											</div>
										</div>
									</li>
									<li>
										<div class="col1">
											<div class="cont">
												<div class="cont-col1">
													<div class="label label-sm label-success">                        
														<i class="fa fa-bullhorn"></i>
													</div>
												</div>
												<div class="cont-col2">
													<div class="desc">
														New order received. Please take care of it.                 
													</div>
												</div>
											</div>
										</div>
										<div class="col2">
											<div class="date">
												40 mins
											</div>
										</div>
									</li>
									<li>
										<div class="col1">
											<div class="cont">
												<div class="cont-col1">
													<div class="label label-sm label-warning">                        
														<i class="fa fa-plus"></i>
													</div>
												</div>
												<div class="cont-col2">
													<div class="desc">
														New user registered.                
													</div>
												</div>
											</div>
										</div>
										<div class="col2">
											<div class="date">
												1.5 hours
											</div>
										</div>
									</li>
									<li>
										<div class="col1">
											<div class="cont">
												<div class="cont-col1">
													<div class="label label-sm label-success">                        
														<i class="fa fa-bell-o"></i>
													</div>
												</div>
												<div class="cont-col2">
													<div class="desc">
														Web server hardware needs to be upgraded. 
														<span class="label label-sm label-default ">Overdue</span>             
													</div>
												</div>
											</div>
										</div>
										<div class="col2">
											<div class="date">
												2 hours
											</div>
										</div>
									</li>
									<li>
										<div class="col1">
											<div class="cont">
												<div class="cont-col1">
													<div class="label label-sm label-default">                       
														<i class="fa fa-bullhorn"></i>
													</div>
												</div>
												<div class="cont-col2">
													<div class="desc">
														New order received. Please take care of it.                 
													</div>
												</div>
											</div>
										</div>
										<div class="col2">
											<div class="date">
												3 hours
											</div>
										</div>
									</li>
									<li>
										<div class="col1">
											<div class="cont">
												<div class="cont-col1">
													<div class="label label-sm label-warning">                        
														<i class="fa fa-bullhorn"></i>
													</div>
												</div>
												<div class="cont-col2">
													<div class="desc">
														New order received. Please take care of it.                 
													</div>
												</div>
											</div>
										</div>
										<div class="col2">
											<div class="date">
												5 hours
											</div>
										</div>
									</li>
									<li>
										<div class="col1">
											<div class="cont">
												<div class="cont-col1">
													<div class="label label-sm label-info">                        
														<i class="fa fa-bullhorn"></i>
													</div>
												</div>
												<div class="cont-col2">
													<div class="desc">
														New order received. Please take care of it.                 
													</div>
												</div>
											</div>
										</div>
										<div class="col2">
											<div class="date">
												18 hours
											</div>
										</div>
									</li>
									<li>
										<div class="col1">
											<div class="cont">
												<div class="cont-col1">
													<div class="label label-sm label-default">                       
														<i class="fa fa-bullhorn"></i>
													</div>
												</div>
												<div class="cont-col2">
													<div class="desc">
														New order received. Please take care of it.                 
													</div>
												</div>
											</div>
										</div>
										<div class="col2">
											<div class="date">
												21 hours
											</div>
										</div>
									</li>
									<li>
										<div class="col1">
											<div class="cont">
												<div class="cont-col1">
													<div class="label label-sm label-info">                        
														<i class="fa fa-bullhorn"></i>
													</div>
												</div>
												<div class="cont-col2">
													<div class="desc">
														New order received. Please take care of it.                 
													</div>
												</div>
											</div>
										</div>
										<div class="col2">
											<div class="date">
												22 hours
											</div>
										</div>
									</li>
									<li>
										<div class="col1">
											<div class="cont">
												<div class="cont-col1">
													<div class="label label-sm label-default">                       
														<i class="fa fa-bullhorn"></i>
													</div>
												</div>
												<div class="cont-col2">
													<div class="desc">
														New order received. Please take care of it.                 
													</div>
												</div>
											</div>
										</div>
										<div class="col2">
											<div class="date">
												21 hours
											</div>
										</div>
									</li>
									<li>
										<div class="col1">
											<div class="cont">
												<div class="cont-col1">
													<div class="label label-sm label-info">                        
														<i class="fa fa-bullhorn"></i>
													</div>
												</div>
												<div class="cont-col2">
													<div class="desc">
														New order received. Please take care of it.                 
													</div>
												</div>
											</div>
										</div>
										<div class="col2">
											<div class="date">
												22 hours
											</div>
										</div>
									</li>
									<li>
										<div class="col1">
											<div class="cont">
												<div class="cont-col1">
													<div class="label label-sm label-default">                       
														<i class="fa fa-bullhorn"></i>
													</div>
												</div>
												<div class="cont-col2">
													<div class="desc">
														New order received. Please take care of it.                 
													</div>
												</div>
											</div>
										</div>
										<div class="col2">
											<div class="date">
												21 hours
											</div>
										</div>
									</li>
									<li>
										<div class="col1">
											<div class="cont">
												<div class="cont-col1">
													<div class="label label-sm label-info">                        
														<i class="fa fa-bullhorn"></i>
													</div>
												</div>
												<div class="cont-col2">
													<div class="desc">
														New order received. Please take care of it.                 
													</div>
												</div>
											</div>
										</div>
										<div class="col2">
											<div class="date">
												22 hours
											</div>
										</div>
									</li>
									<li>
										<div class="col1">
											<div class="cont">
												<div class="cont-col1">
													<div class="label label-sm label-default">                       
														<i class="fa fa-bullhorn"></i>
													</div>
												</div>
												<div class="cont-col2">
													<div class="desc">
														New order received. Please take care of it.                 
													</div>
												</div>
											</div>
										</div>
										<div class="col2">
											<div class="date">
												21 hours
											</div>
										</div>
									</li>
									<li>
										<div class="col1">
											<div class="cont">
												<div class="cont-col1">
													<div class="label label-sm label-info">                        
														<i class="fa fa-bullhorn"></i>
													</div>
												</div>
												<div class="cont-col2">
													<div class="desc">
														New order received. Please take care of it.                 
													</div>
												</div>
											</div>
										</div>
										<div class="col2">
											<div class="date">
												22 hours
											</div>
										</div>
									</li>
								</ul>
							</div>
						</div>
						<div class="tab-pane" id="tab_1_2">
							<div class="scroller" style="height: 290px;" data-always-visible="1" data-rail-visible1="1">
								<ul class="feeds">
									<li>
										<a href="#">
											<div class="col1">
												<div class="cont">
													<div class="cont-col1">
														<div class="label label-sm label-success">                        
															<i class="fa fa-bell-o"></i>
														</div>
													</div>
													<div class="cont-col2">
														<div class="desc">
															New user registered
														</div>
													</div>
												</div>
											</div>
											<div class="col2">
												<div class="date">
													Just now
												</div>
											</div>
										</a>
									</li>
									<li>
										<a href="#">
											<div class="col1">
												<div class="cont">
													<div class="cont-col1">
														<div class="label label-sm label-success">                        
															<i class="fa fa-bell-o"></i>
														</div>
													</div>
													<div class="cont-col2">
														<div class="desc">
															New order received 
														</div>
													</div>
												</div>
											</div>
											<div class="col2">
												<div class="date">
													10 mins
												</div>
											</div>
										</a>
									</li>
									<li>
										<div class="col1">
											<div class="cont">
												<div class="cont-col1">
													<div class="label label-sm label-danger">                      
														<i class="fa fa-bolt"></i>
													</div>
												</div>
												<div class="cont-col2">
													<div class="desc">
														Order #24DOP4 has been rejected.    
														<span class="label label-sm label-danger ">Take action <i class="fa fa-share"></i></span>
													</div>
												</div>
											</div>
										</div>
										<div class="col2">
											<div class="date">
												24 mins
											</div>
										</div>
									</li>
									<li>
										<a href="#">
											<div class="col1">
												<div class="cont">
													<div class="cont-col1">
														<div class="label label-sm label-success">                        
															<i class="fa fa-bell-o"></i>
														</div>
													</div>
													<div class="cont-col2">
														<div class="desc">
															New user registered
														</div>
													</div>
												</div>
											</div>
											<div class="col2">
												<div class="date">
													Just now
												</div>
											</div>
										</a>
									</li>
									<li>
										<a href="#">
											<div class="col1">
												<div class="cont">
													<div class="cont-col1">
														<div class="label label-sm label-success">                        
															<i class="fa fa-bell-o"></i>
														</div>
													</div>
													<div class="cont-col2">
														<div class="desc">
															New user registered
														</div>
													</div>
												</div>
											</div>
											<div class="col2">
												<div class="date">
													Just now
												</div>
											</div>
										</a>
									</li>
									<li>
										<a href="#">
											<div class="col1">
												<div class="cont">
													<div class="cont-col1">
														<div class="label label-sm label-success">                        
															<i class="fa fa-bell-o"></i>
														</div>
													</div>
													<div class="cont-col2">
														<div class="desc">
															New user registered
														</div>
													</div>
												</div>
											</div>
											<div class="col2">
												<div class="date">
													Just now
												</div>
											</div>
										</a>
									</li>
									<li>
										<a href="#">
											<div class="col1">
												<div class="cont">
													<div class="cont-col1">
														<div class="label label-sm label-success">                        
															<i class="fa fa-bell-o"></i>
														</div>
													</div>
													<div class="cont-col2">
														<div class="desc">
															New user registered
														</div>
													</div>
												</div>
											</div>
											<div class="col2">
												<div class="date">
													Just now
												</div>
											</div>
										</a>
									</li>
									<li>
										<a href="#">
											<div class="col1">
												<div class="cont">
													<div class="cont-col1">
														<div class="label label-sm label-success">                        
															<i class="fa fa-bell-o"></i>
														</div>
													</div>
													<div class="cont-col2">
														<div class="desc">
															New user registered
														</div>
													</div>
												</div>
											</div>
											<div class="col2">
												<div class="date">
													Just now
												</div>
											</div>
										</a>
									</li>
									<li>
										<a href="#">
											<div class="col1">
												<div class="cont">
													<div class="cont-col1">
														<div class="label label-sm label-success">                        
															<i class="fa fa-bell-o"></i>
														</div>
													</div>
													<div class="cont-col2">
														<div class="desc">
															New user registered
														</div>
													</div>
												</div>
											</div>
											<div class="col2">
												<div class="date">
													Just now
												</div>
											</div>
										</a>
									</li>
									<li>
										<a href="#">
											<div class="col1">
												<div class="cont">
													<div class="cont-col1">
														<div class="label label-sm label-success">                        
															<i class="fa fa-bell-o"></i>
														</div>
													</div>
													<div class="cont-col2">
														<div class="desc">
															New user registered
														</div>
													</div>
												</div>
											</div>
											<div class="col2">
												<div class="date">
													Just now
												</div>
											</div>
										</a>
									</li>
								</ul>
							</div>
						</div>
						<div class="tab-pane" id="tab_1_3">
							<div class="scroller" style="height: 290px;" data-always-visible="1" data-rail-visible1="1">
								<div class="row">
									<div class="col-md-6 user-info">
										<img alt="" src="assets/img/avatar.png" class="img-responsive" />
										<div class="details">
											<div>
												<a href="#">Robert Nilson</a> 
												<span class="label label-sm label-success label-mini">Approved</span>
											</div>
											<div>29 Jan 2013 10:45AM</div>
										</div>
									</div>
									<div class="col-md-6 user-info">
										<img alt="" src="assets/img/avatar.png" class="img-responsive" />
										<div class="details">
											<div>
												<a href="#">Lisa Miller</a> 
												<span class="label label-sm label-info">Pending</span>
											</div>
											<div>19 Jan 2013 10:45AM</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6 user-info">
										<img alt="" src="assets/img/avatar.png" class="img-responsive" />
										<div class="details">
											<div>
												<a href="#">Eric Kim</a> 
												<span class="label label-sm label-info">Pending</span>
											</div>
											<div>19 Jan 2013 12:45PM</div>
										</div>
									</div>
									<div class="col-md-6 user-info">
										<img alt="" src="assets/img/avatar.png" class="img-responsive" />
										<div class="details">
											<div>
												<a href="#">Lisa Miller</a> 
												<span class="label label-sm label-danger">In progress</span>
											</div>
											<div>19 Jan 2013 11:55PM</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6 user-info">
										<img alt="" src="assets/img/avatar.png" class="img-responsive" />
										<div class="details">
											<div>
												<a href="#">Eric Kim</a> 
												<span class="label label-sm label-info">Pending</span>
											</div>
											<div>19 Jan 2013 12:45PM</div>
										</div>
									</div>
									<div class="col-md-6 user-info">
										<img alt="" src="assets/img/avatar.png" class="img-responsive" />
										<div class="details">
											<div>
												<a href="#">Lisa Miller</a> 
												<span class="label label-sm label-danger">In progress</span>
											</div>
											<div>19 Jan 2013 11:55PM</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6 user-info">
										<img alt="" src="assets/img/avatar.png" class="img-responsive" />
										<div class="details">
											<div><a href="#">Eric Kim</a> <span class="label label-sm label-info">Pending</span></div>
											<div>19 Jan 2013 12:45PM</div>
										</div>
									</div>
									<div class="col-md-6 user-info">
										<img alt="" src="assets/img/avatar.png" class="img-responsive" />
										<div class="details">
											<div>
												<a href="#">Lisa Miller</a> 
												<span class="label label-sm label-danger">In progress</span>
											</div>
											<div>19 Jan 2013 11:55PM</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6 user-info">
										<img alt="" src="assets/img/avatar.png" class="img-responsive" />
										<div class="details">
											<div><a href="#">Eric Kim</a> <span class="label label-sm label-info">Pending</span></div>
											<div>19 Jan 2013 12:45PM</div>
										</div>
									</div>
									<div class="col-md-6 user-info">
										<img alt="" src="assets/img/avatar.png" class="img-responsive" />
										<div class="details">
											<div>
												<a href="#">Lisa Miller</a> 
												<span class="label label-sm label-danger">In progress</span>
											</div>
											<div>19 Jan 2013 11:55PM</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6 user-info">
										<img alt="" src="assets/img/avatar.png" class="img-responsive" />
										<div class="details">
											<div>
												<a href="#">Eric Kim</a> 
												<span class="label label-sm label-info">Pending</span>
											</div>
											<div>19 Jan 2013 12:45PM</div>
										</div>
									</div>
									<div class="col-md-6 user-info">
										<img alt="" src="assets/img/avatar.png" class="img-responsive" />
										<div class="details">
											<div>
												<a href="#">Lisa Miller</a> 
												<span class="label label-sm label-danger">In progress</span>
											</div>
											<div>19 Jan 2013 11:55PM</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!--END TABS-->
			</div>
		</div>
		<!-- END PORTLET-->
	</div>
</div>
--]