<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/static/static.jsp"></jsp:include>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=yes, initial-scale=1.0, maximum-scale=2.0, minimum-scale=0.5">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="renderer" content="webkit">
    <title>用户管理</title>
    <link rel="stylesheet" href="${ctx}/common/font-awesome.css"/>
    <link rel="stylesheet" href="${ctx}/common/element.css">
    <link rel="stylesheet" href="${ctx}/css/base.css"/>
    <link rel="stylesheet" href="${ctx}/css/queryPanel.css"/>
    <script src="${ctx}/common/jquery-1.12.4.min.js"></script>
    <script src="${ctx}/common/vue.js"></script>
    <script src="${ctx}/common/vue-resource.min.js"></script>
    <script src="${ctx}/common/element.js"></script>
    <script src="${ctx}/common/array.js"></script>
	<script src="${ctx}/common/store.modern.min.js"></script>
    <script src="${ctx}/js/base.js"></script>
    <style type="text/css">
    	.widthcss{
    		width:168px;
    	}
     .el-checkbox{
		width: 180px;
		margin-left:15px;
		margin-top:5px;
	  }
	  .el-form-item__content{
	  	margin-left:10px!important;
	  }
    </style>
</head>
<body>
<div id="app" class="v__addAnimationBg">
      <!-- 查询条件begin -->
	      <el-row style="padding:0px 0px;width: 100%;top: 8px;background-color:#FAFAFA;">
		        <el-col :span="22" >
		           	<el-form :model="queryForm" :inline="true" ref="queryForm" >
		           		<el-form-item label="所在科室"  label-width="80px"  >
							<el-select class="widthcss" v-model="queryForm.DEPTID" placeholder="请选择">
								<el-option label="全部" value=""></el-option>
						        <el-option v-for="item in depOptions" :key="item.ID" :label="item.LABEL" :value="item.ID">
						   </el-select>
					    </el-form-item>
					    <el-form-item label="人员性质"  label-width="70px"  >
					    	<el-select class="widthcss" v-model="queryForm.NATURE" placeholder="请选择">
					    		<el-option label="全部" value=""></el-option>
								<el-option v-for="item in natureOptions" :key="item.CODE" :label="item.NAME" :value="item.CODE" ></el-option>
						   </el-select>
					    </el-form-item>
					    <el-form-item label="姓名"  label-width="40px"  >
					    	<el-input clearable v-model="queryForm.USERNAME" ></el-input>
					    </el-form-item>
			 			<el-button type="primary" size="small" style="margin-top:8px;" @click="bindTableData" ><i class="fa fa-search"></i> 查询</el-button>
			 			<el-button type="primary" size="small" style="margin-top:8px;" @click="uploadSignPicture" ><i class="fa el-icon-upload2"></i>上传签名图片</el-button>
						
				  </el-form>
		        </el-col>
		      <el-col :span="2" style="text-align: right;margin-top: 8px;">
		        	<el-button type="primary" size="small" @click="handleSetRole" ><i class="fa el-icon-setting" aria-hidden="true"></i> 分配角色</el-button>
	        </el-col>
		  </el-row>
	<!-- 查询条件 end -->	
      <el-row style="position: absolute;top: 55px;bottom: 0;width: 100%">
        
        <!-- 列表 begin -->
         <el-col :span="24" style="height: 100%; position: relative;">
	   		<el-row class="v__tableDock" style="position: absolute;top: 0px;bottom: 30px;width: 100%">
		        <el-col :span="14">
		            <el-table :data="tableData" :height="v__tableDockHeight" stripe border style="width: 100%;" 
		            	v-loading.fullscreen.lock="tableLoading"  highlight-current-row 
				              @row-click="handleCurrentRow">
		                  <el-table-column  type="index" label="序号" width="55" align="center"></el-table-column>
						  <el-table-column prop="DEPARTMENT" label="科室" min-width="160"> </el-table-column>
						  <el-table-column prop="USERNAME" label="姓名" min-width="100"></el-table-column>
						  <el-table-column prop="LOGON_NAME" label="登录名" min-width="80"> </el-table-column>
						  <el-table-column prop="DUTYNAME" label="职务" min-width="100"> </el-table-column>
						  <el-table-column prop="NATURENAME" label="人员性质" min-width="100"> </el-table-column>
		            </el-table>
		        </el-col>
		        <el-col :span="10" style="height: 100%; padding-right: 10px; border: 1px solid #eee;">
		        <template>
		        	
		        	<div style="margin-top: 20px" v-loading="checkLoading">
		        	<el-form ref="addRoleForm" :model="addRoleForm" label-width="80px" size="mini">
		        	  <el-form-item label="角色列表：">
		        	  <br>
					  <el-checkbox-group  size="mini"  v-model="addRoleForm.checkList">
					    <el-checkbox name="type" v-for="role in rolelist" :label="role.ROLE_ID" :key="role.ROLE_ID" 
					    border style="margin-left: 20px; " :disabled="disabledCheckbox">{{role.NAME}}</el-checkbox>
					  </el-checkbox-group>
					  </el-form-item>
					  </el-form>
				  </div>
				</template>
		        </el-col>
		    </el-row>
		    <el-row style="position: absolute;bottom: 0;">
		        <el-col :span="24">
					<el-pagination
				       @size-change="handleSizeChange"
		       		   @current-change="handleCurrentChange"
				      :page-sizes="[20, 50, 100]"
				      :page-size="page.pagesize"
				      layout="total, sizes, prev, pager, next, jumper"
				      :total="page.total" style="margin-top:10px;">
				    </el-pagination>
		        </el-col>
		    </el-row>
        </el-col> 
      <!-- 列表end -->	  
      <!-- 图片上传begin -->
		<el-dialog  title="图片管理" :visible.sync="addUploadDialog" :close-on-click-modal="false":modal-append-to-body='false' top="5%" >
			<el-upload :action="action" list-type="picture-card" :before-upload="beforeUpload"  :accept="fileFormat" :limit="1"
					 name="file" ref="file" :auto-upload=true :file-list="fileList" :on-preview="handlePictureCardPreview"
					 :on-remove="removePicture" :on-exceed="handleExceed">
				<i class="el-icon-plus">添加图片</i>
				<div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
			</el-upload>
			<el-dialog :visible.sync="dialogVisible" size="tiny" :modal="false">
					<img width="100%" :src="dialogImageUrl" alt="">
			</el-dialog>
			<div slot="footer" class="dialog-footer">
				<el-button type="primary" size="small" @click="addUploadDialog = false">关闭</el-button>
			</div>
		</el-dialog>
  </el-row> 		
</div>
</body>
<script type="text/javascript">
	Vue.http.options.emulateJSON = true;
	var vo = new Vue({
		el:"#app",
		data:function(){
			return{
				token:store.get('token'),
				fileFormat:'.jpg,.png,.jpeg',//只能上传这2中格式的图片
				action:'',
				dialogImageUrl:'',
				fileList:[],
				tableData:[],
				depOptions:[],
				natureOptions:[],
				dutyOptions:[],
				postOptions:[],
				tableData : [],
				v__tableDockHeight:0,
				formLabelWidth:'110px',
				dialogAddFormVisible: false,
				dialogAddLwFormVisible: false,
				tableLoading: false,
				checkLoading: false,
				dlgAddRoleVisible:false,
				fullscreenLoading1: false,
				currentRow:null,//表格当前行
				defaultCheckedKeys:[],//默认选中角色
				disabledCheckbox:true,//角色列表是否只读
				disabledOrg:false,
				disabledLw:false,
				addUploadDialog:false,
				dialogVisible:false,
		        page:{
		        	total:0 ,
		        	pagesize:20,
		        	currentpage:1
		        },
		        rolelist:[],
		        addRoleForm:{
		        	checkList: [],
		        },
   		        queryForm:{
   		        	USERNAME:'',
   		        	DEPTID:'',
   		        	NATURE:''
   		        }
			}
		},
		mounted:function(){
            this.$nextTick(function() {
                vo.bindTableData();
                vo.getDepSelectItem();//部门
                vo.getRyxzSelectItem();
                vo.getCheckboxItem();//角色
			  })
		},
		methods:{
			  getDepSelectItem: function(){//所在科室
					  vo.$http.get("${ctx}/emp/queryDepSelect",
								 {   params: {"token":vo.token}
		 						    }).then(function(res){ 
		 						    	vo.depOptions=res.body; 
				 		}); 
		  	  },
	  	      getRyxzSelectItem: function(){//人员性质
				  vo.$http.get("${ctx}/emp/getSelectItem",
							 {   params: {"token":vo.token,"tCode":"12"}
	 						    }).then(function(res){ 
	 						    	vo.natureOptions=res.body; 
			 		}); 
	  	      },
		  	  getCheckboxItem: function(){//所有角色
		  		  vo.checkLoading=true;
				  vo.$http.get("${ctx}/role/getRoleList",
							 {   params: {"token":vo.token,"pagesize":1,"currentpage":100}
	 						    }).then(function(res){ 
	 						    	vo.rolelist=res.body; 
	 						    	vo.checkLoading=false;
			 		}); 
	  	  	  },
	 		 bindTableData: function(){//表格数据
	 			vo.tableLoading=true;
	 			vo.currentRow=null;
				vo.disabledCheckbox=true;
				vo.addRoleForm.checkList=[];
			   vo.$http.get("${ctx}/emp/pageEmp",
						 {   params: {  
								"token":vo.token,
						  		"pagesize":vo.page.pagesize,
						        "currentpage":vo.page.currentpage,
						        "DEPTID":vo.queryForm.DEPTID,
						        "USERNAME":vo.queryForm.USERNAME,
						        "NATURE":vo.queryForm.NATURE,
						        "ENABLED":"0"
						    }}).then(function(res){ 
						    	
						 vo.page.currentpage=res.body.currentpage;
					 	 vo.page.total=res.body.total;
						 vo.tableData=res.body.list;  
						 vo.tableLoading=false;
					
				 });   
			},
		handleCurrentRow: function(row){//点击表格行
			vo.currentRow=row;
			vo.disabledCheckbox=false;
		    vo.$http.get("${ctx}/role/getRoleIdsByUserId",
					 {   params: {"token":vo.token,"userId":vo.currentRow.GUID}
					    }).then(function(res){ 
					    	
					    	if(res.bodyText != ""&& res.bodyText != null){
					    		vo.addRoleForm.checkList=res.bodyText.split(","); 
					    	}else{
					    		vo.addRoleForm.checkList=[];
					    	}
					    	
	 		}); 
		},
		handleCurrentChange: function(val){//当前页改变
			vo.page.currentpage=val;//当前页
			vo.bindTableData();
		 },
		handleSizeChange:function(val) {//每页显示多少条
			 vo.page.pagesize=val;
			 vo.page.currentpage=1;
			 vo.bindTableData();
	      },
		handleSetRole: function(){//分配角色
			if(vo.currentRow==null){
	        	  vo.$message({
	        		 message: '请先选择一条人员数据！',
		              type: 'warning' 
		            });
	        	  return;
	          }
		 vo.$http.get("${ctx}/user/setUserRole",
				 {   params: {"token":vo.token, "guid":vo.currentRow.GUID,"roleid":vo.addRoleForm.checkList+""  }
	        }).then(function(res){ 
				  		if(res.ok){
				  			 vo.$message({
					              message: '操作成功！',
					              duration:1000 ,
					              type: 'success' ,
						              onClose:function(){
						            	vo.bindTableData();
						              } 
					            });
				  			
				  		}else{
				  			vo.$message.error('操作失败！');
				  		}
			}); 
		},
		//签名图片上传
		uploadSignPicture:function(){
			if(vo.currentRow==null){
				 vo.$message({
		              message: '请先点击行信息！',
		              type: 'warning' ,
		            });
				 return ;
			};
			vo.fileList=[];
			vo.action='${ctx}/user/uploadPicture?token='+store.get("token")+'&GUID='+vo.currentRow.GUID;
			vo.$http.post("${ctx}/user/selectScanPictures",
					 {"token":vo.token,"guid":vo.currentRow.GUID
					}).then(function(data){ 
					if(data.body.length>0){
						vo.fileList=[data.body[0]];
					}else{
						vo.fileList=[];
					}
	 		}); 
			vo.addUploadDialog=true;
		},
		//上传前验证
		beforeUpload:function(file){
			var flag = true;
			const fileData=file.size/1024/1024;
			if(fileData>=200){
				vo.$message.error('上传文件大小不能超过200MB');
				flag=false;
			};
			return flag;
		},
		//图片查看
		handlePictureCardPreview:function(file) {
			vo.dialogImageUrl = file.url;
			vo.dialogVisible = true; 
		},
		//图片移除
		removePicture:function(file,fileList){
			vo.$http.post('${ctx}/user/deletePicutre',{
				'token':vo.token,
				'id':file.id
			}).then(function(data){
				if(data.body>0){
					vo.$message({
						type:'success',
						message:'删除成功!'
					})
				}else{
					vo.$message({
						type:'error',
						message:'删除失败!'
					})
				}
			})
		},
		 handleExceed:function(files, fileList) {
			 var account=files.length + fileList.length;
	        this.$message.warning('当前限制选择 1个图片，本次选择了 '+files.length+'个图片，共选择了'+account+' 个图片');
	      },
		}
		});
		

	 Hp.createNew(vo)
</script>