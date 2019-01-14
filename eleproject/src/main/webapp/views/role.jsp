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
    <title>角色</title>
    <link rel="stylesheet" href="${ctx}/common/font-awesome.css"/>
    <link rel="stylesheet" href="${ctx}/common/element.css"/>
    <link rel="stylesheet" href="${ctx}/css/base.css"/>
    <script src="${ctx}/common/vue.js"></script>
    <script src="${ctx}/common/vue-resource.min.js"></script>
    <script src="${ctx}/common/element.js"></script>
    <script src="${ctx}/common/store.modern.min.js"></script>
    <script src="${ctx}/js/base.js"></script>
</head>
<body style="overflow:hidden;">
<div id="app" class="v__addAnimationBg">

	<!-- 列表 begin -->
	    <el-row>
	        <el-col :span="18">
	            <el-button type="primary" size="small" icon="el-icon-plus" @click="addRole"> 新增</el-button>
	            <el-button type="primary" size="small" icon="el-icon-edit" @click="handleEdit">编辑</el-button>
	            <el-button type="primary" size="small" icon="el-icon-delete" @click="handleDelete">删除</el-button>
	            <el-button type="primary" size="small" icon="el-icon-setting" @click="handleAddMenu">分配权限</el-button>
	        </el-col>
	    </el-row>
	    <el-row class="v__tableDock" style="position: absolute;top: 35px;bottom: 30px;width: 100%">
	        <el-col :span="24">
	            <el-table :data="tableData" :height="v__tableDockHeight" stripe border style="width: 100%;" v-loading.fullscreen.lock="fullscreenLoading"  highlight-current-row 
			              @row-click="handleCurrentRow">
	                  <el-table-column  type="index" label="序号" width="80" align="center"></el-table-column>
					  <el-table-column prop="CODE_NAME" label="角色代码" min-width="200"> </el-table-column>
					  <el-table-column prop="NAME" label="角色名称" min-width="200"> </el-table-column>
					  <el-table-column prop="PNAME" label="父级角色" min-width="200"> </el-table-column>
					  <el-table-column prop="DESCRIPTION" label="描述" min-width="400"> </el-table-column>
	            </el-table>
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
    	<!-- 列表 end -->		
		
		
<!-- 新增 begin -->
		<el-dialog ref="dlgAdd" size="small" top="5%" title="新增角色"  :visible.sync="dialogAddFormVisible" 
			:modal-append-to-body="false" :close-on-click-modal="false">
		  <el-form :model="form" :rules="rules" ref="form" >
		  	 <el-form-item label="角色编码" :label-width="formLabelWidth" prop="CODE_NAME">
		      <el-input size="small" v-model="form.CODE_NAME" auto-complete="off"></el-input>
		    </el-form-item>
		    <el-form-item label="角色名称" :label-width="formLabelWidth" prop="NAME">
		      <el-input size="small" v-model="form.NAME" auto-complete="off" ></el-input>
		    </el-form-item>
		    <el-form-item label="父级角色" :label-width="formLabelWidth"  prop="PARENT_GUID">
		      <el-select  size="small"v-model="form.PARENT_GUID" clearable  placeholder="请选择">
		         <el-option v-for="item in parentroles" :key="item.ROLE_ID" :label="item.NAME" :value="item.ROLE_ID" ></el-option>
		      </el-select>
		    </el-form-item>
		    	<el-form-item label="是否是管理员" :label-width="formLabelWidth" prop="ISADMIN">
			      	<el-radio class="radio" v-model="form.ISADMIN" label="0">是</el-radio>
	  				<el-radio class="radio" v-model="form.ISADMIN" label="1">否</el-radio>
		    </el-form-item>
		    <el-form-item label="排序号" :label-width="formLabelWidth" prop="IDX"  >
		      <el-input size="small" v-model.number="form.IDX" auto-complete="off"></el-input>
		    </el-form-item>
		    <el-form-item label="角色描述" :label-width="formLabelWidth"  prop="DESCRIPTION">
		      <el-input  type="textarea"  :rows="3" v-model="form.DESCRIPTION" auto-complete="off" ></el-input>
		    </el-form-item>

		  </el-form>
		  <div slot="footer" class="dialog-footer">
		    <el-button size="small" @click="dialogAddFormVisible = false">取 消</el-button>
		    <el-button type="primary" size="small" @click="saveRole('form')">确认</el-button>
		  </div>
		</el-dialog>
	<!-- 新增 end -->	
	
	<!-- 修改 begin -->
		<el-dialog ref="dlgEdit" title="编辑角色" size="small" top="5%" :visible.sync="dialogEditFormVisible"
			:modal-append-to-body="false" :close-on-click-modal="false">
		  
		  <el-form :model="editForm" :rules="rules" ref="editForm" >
		    <el-input v-model="editForm.ROLE_ID" auto-complete="off" style="display: none;"></el-input> 
		    <el-form-item label="角色编码" :label-width="formLabelWidth" prop="CODE_NAME">
		      <el-input size="small" v-model="editForm.CODE_NAME" auto-complete="off"></el-input>
		    </el-form-item>
		    <el-form-item label="角色名称" :label-width="formLabelWidth" prop="NAME">
		      <el-input size="small" v-model="editForm.NAME" auto-complete="off" ></el-input>
		    </el-form-item>
		    <el-form-item label="父级角色" :label-width="formLabelWidth">
		      <el-select size="small" v-model="editForm.PARENT_GUID" clearable  placeholder="请选择">
		         <el-option v-for="item in parentroles" :key="item.ROLE_ID" :label="item.NAME" :value="item.ROLE_ID" ></el-option>
		      </el-select>
		    </el-form-item>
		    	<el-form-item label="是否是管理员" :label-width="formLabelWidth" prop="ISADMIN">
			      	<el-radio class="radio" v-model="editForm.ISADMIN" label="0">是</el-radio>
	  				<el-radio class="radio" v-model="editForm.ISADMIN" label="1">否</el-radio>
		    </el-form-item>
		    <el-form-item label="排序号" :label-width="formLabelWidth" prop="IDX"  >
		      <el-input size="small" v-model.number="editForm.IDX" auto-complete="off"></el-input>
		    </el-form-item>
		    <el-form-item label="角色描述" :label-width="formLabelWidth">
		      <el-input  type="textarea"  :rows="3" v-model="editForm.DESCRIPTION" auto-complete="off" ></el-input>
		    </el-form-item>

		  </el-form>
		  <div slot="footer" class="dialog-footer">
		    <el-button size="small" @click="dialogEditFormVisible = false">取 消</el-button>
		    <el-button type="primary" size="small" @click="updateRole('editForm')">确认</el-button>
		  </div>
		</el-dialog>
	<!-- 修改end -->	
	
	<!-- 分配权限begin -->
			<el-dialog ref="dlgAddMenu" title="分配权限" size="small" top="5%" :visible.sync="dialogAddMenu"   v-loading.fullscreen.lock="fullscreenLoading1" 
				:modal-append-to-body="false" :close-on-click-modal="false" >
			  <el-form :model="form" :rules="rules" ref="form" style="height:450px;">
			     <el-tree :data="treedata"  :highlight-current="true" accordion  show-checkbox 
	   		 		 ref="tree" node-key="menu_id" :default-checked-keys="defaultCheckedKeys" style="height:450px;overflow:auto"></el-tree>
	
			  </el-form>
			  <div slot="footer" class="dialog-footer">
			    <el-button size="small" @click="dialogAddMenu = false">取 消</el-button>
			    <el-button type="primary" size="small" @click="saveRoleToMenu()">确认</el-button>
			  </div>
		</el-dialog>
	<!-- 分配权限end -->	
	
</div>
</body>
<script>
    Vue.http.options.emulateJSON = true;
    var vo = new Vue({
        el: "#app",
        data : function() {
			return {
				
				token:store.get("token"),
				tableData : [],
				v__tableDockHeight: 0,
				formLabelWidth: '100px',
			    parentroles: [  ],
		        dialogAddFormVisible: false,
		        dialogEditFormVisible: false,
		        fullscreenLoading: true,
		        fullscreenLoading1: false,
		        dialogAddMenu:false,
		        treedata: [],
		        menu_ids:'',//权限ids
		        addMenuRoleId:"",//给分配权限时的roleID
		        addMenuPARENT_GUID:"",//给分配权限时的PARENT_GUID
		        defaultCheckedKeys:[],//默认选中权限
		        total_rows: 0,
		        currentRow: null,
		        radio: '',
		        form: {
		        	NAME: '',
		        	CODE_NAME: '',
		        	DESCRIPTION: '',
		        	PARENT_GUID: '',
		        	IDX:0,
		        	ISADMIN:'1'
		           
		        },
		        editForm: {
		        	ROLE_ID:'',
		        	NAME: '',
		        	CODE_NAME: '',
		        	DESCRIPTION: '',
		        	PARENT_GUID: '',
		        	IDX:0,
		        	ISADMIN:''
		           
		        },
		        page:{
		        	total:0 ,
		        	pagesize:20,
		        	currentpage:1
		        },
			
		        rules: {
		        	NAME: [
		 	              { required: true, message: '请输入角色名称'}/* ,
		 	              { min: 3, max: 5, message: '长度在 3 到 5 个字符'} */
		 	            ],
	 	            CODE_NAME: [
	 	              { required: true, message: '请输入角色代码'}
	 	            ]  ,
	 	            IDX: [ 	    { required: true, message: '排序号不能为空'}/* ,
	 	                       { type: 'number', message: '排序号必须为数字值'} */
	 			 	            ]  
		        }
			}
		},
        mounted: function () {
            //页面装载完毕后加载表格数据
            this.$nextTick(function() {
			    // 保证vo.$el已经插入文档
			    vo.bindTableData();
			  })
        },
        methods: {
        	resetForm:function(formName) {// 重置表单
        		if(vo.$refs.hasOwnProperty('form'))//判断是否包括form表单
  	            	vo.$refs.form.resetFields();
  	          },
 	 		bindTableData:function(){//表格数据
 	 				
 	 				 vo.$http.get("${ctx}/role/getRolePage",
 	 						 {   params: {  
 	 								"token":vo.token,
 	 						  		"pagesize":vo.page.pagesize,
 	 						        "currentpage":vo.page.currentpage  
 	 						    }}).then(function(res){ 
 	 						    	
 	 						 vo.page.currentpage=res.body.currentpage;
 	 					 	 vo.page.total=res.body.total;
 	 						 vo.tableData=res.body.list; 
 	 						 vo.fullscreenLoading=false;
 	 					
 	 				});  
 	 			},
 			handleCurrentChange:function(val){//当前页改变
 				vo.page.currentpage=val;//当前页
 				vo.bindTableData();
 				
 			},
 			 handleSizeChange:function(val) {//每页显示多少条
 				 vo.page.pagesize=val;
 				 vo.page.currentpage=1;
 				 vo.bindTableData();
 		      },
 			getParentRole:function(){//获取父集角色
 				 vo.$http.get("${ctx}/role/getRoleList",
 						 {   params: {  
 							"token":vo.token,
 					        "PARENT_GUID":0  
 					    }}).then(function(res){  
 					    	
 						vo.parentroles=res.body;
 					    });  
 			},
 			addRole:function(){//显示添加模板
 				
 				vo.dialogAddFormVisible = true;
 				vo.resetForm();	// 重置表单
 				vo.getParentRole();
 				//Hp.showDlg(vo.$refs.dlgAdd);
 				
 			},
 			saveRole:function(form){//保存信息
 				vo.$refs[form].validate(function(valid)  {
 			          if (valid) {
 			        	 vo.form["token"] = vo.token; 
 						var formData = JSON.stringify(vo.form);
 						  
 						var url="${ctx}/role/saveRole";
 					  	vo.$http.post(url,formData ).then(function(res){  
 					   
 					  		if(res.ok){
 					  			 vo.$message({
 						              message: '保存成功！',
 						              type: 'success',
 						              duration:1000, 
 						             // showClose:true,
 						              onClose:function(){
 						            	 vo._data.dialogAddFormVisible = false;
 						            	 vo.bindTableData();
 						              } 
 						            });
 					  		}else{
 					  			vo.$message.error('保存失败！');
 					  		}
 							    	 
 						});   
 			          } else {
 			            return false;
 			          }
 			        });
 				
 			},
 			 handleEdit:function() {//显示修改模块
 				 if( vo.judeCurrentRowIsNull('编辑')){
 					vo.dialogEditFormVisible = true;
 					//Hp.showDlg(vo.$refs.dlgEdit);
 					 vo.getParentRole();
 					 vo.$http.get("${ctx}/role/getRole",
 							 {   params: {"token":vo.token, "ROLE_ID":vo.currentRow.ROLE_ID }
 						    }).then(function(res){  
 						    	vo.editForm=res.body;
 						    	if(res.body.PARENT_GUID=="0"){
 						    		vo.editForm.PARENT_GUID="";
 						    	}
 					});  
 				 }
 		      },
 		      updateRole:function(editForm){//修改信息
 					 vo.$refs[editForm].validate(function(valid)  {
 				          if (valid) {
 				        	 vo.editForm["token"] = vo.token; 
 							var formData = JSON.stringify(vo.editForm);
 							  
 							var url="${ctx}/role/updateRole";
 						  	vo.$http.post(url,formData ).then(function(res){  
 						   
 						  		if(res.ok){
 						  			 vo.$message({
 							              message: '编辑成功！',
 							              type: 'success',
 							              duration:1000 ,
 							              //showClose:true,
 							              onClose:function(){
 							            	 vo._data.dialogEditFormVisible = false;
 							            	 vo.bindTableData();
 							              } 
 							            });
 						  		}else{
 						  			vo.$message.error('编辑失败！');
 						  		}
 								    	 
 							});   
 				          } else {
 				            return false;
 				          }
 				        });
 					
 				},
 		      handleDelete:function() {//删除信息
 		    	  
 		    	 if( vo.judeCurrentRowIsNull('删除')){
 		    		 //判断是否有子菜单，若有则先删除子菜单
 			    	   vo.$http.get("${ctx}/role/getRoleList",
 									 {   params: { "token":vo.token,"PARENT_GUID":vo.currentRow.ROLE_ID }
 					        }).then(function(res){ 
 								  	  	if(res.body.length>0){
 								  			 vo.$message({
 									              message: '请先删除子角色！',
 									              duration:1000 ,
 									              type: 'warning' 
 									            });
 								  			
 								  		}else{
 								  		    vo.$confirm('确定删除该角色?', '提示', {
 									              confirmButtonText: '确定',
 									              cancelButtonText: '取消',
 									              type: 'warning'
 									            }).then(function()  {
 									            	 vo.$http.get("${ctx}/role/deleteRole",
 															 {   params: {"token":vo.token, "ROLE_ID":vo.currentRow.ROLE_ID }
 											        }).then(function(res){ 
 											        	
 														  		if(res.ok){
 														  			 vo.$message({
 															              message: '删除成功！',
 															              duration:1000 ,
 															              type: 'success' ,
 								 							              onClose:function(){
 								 							            	vo.bindTableData();
 								 							              } 
 															            });
 														  			
 														  		}else{
 														  			vo.$message.error('删除失败！');
 														  		}
 													});  
 											        
 									            }).catch(function()  {
 									            });
 								  		} 
 							});  
 		    	 }
 		    	 
 		      },
  	         handleAddMenu:function(){//分配权限
 	        	 if( vo.judeCurrentRowIsNull('分配权限')){
 					 vo.dialogAddMenu=true; 
 					 vo.fullscreenLoading1=true; 
 					 //给分配权限时的roleID，PARENT_GUID赋值
 					 vo.addMenuRoleId=vo.currentRow.ROLE_ID;
 					 vo.addMenuPARENT_GUID=vo.currentRow.PARENT_GUID;
 					 //默认选中
	 					 vo.$http.get("${ctx}/roleToMenu/getMenuIds",
	 							 {   params: {  
	 								"token":vo.token,"ROLE_ID":vo.currentRow.ROLE_ID  
	 							    }}).then(function(res){ 
	 						 vo.defaultCheckedKeys=res.body.menuids;
	 					}); 
 					 vo.$http.get("${ctx}/menu/queryMenuTree",
 							 {   params: {  
  								"token":vo.token  
  							    }}).then(function(res){ 
 						 vo.treedata=res.body;
 						 vo.fullscreenLoading1=false;
 					
 					});  
 				 
 	        	 }
 			},
 		      saveRoleToMenu:function() {//保存角色菜单关联记录
 		    	  
 		    	  vo.menu_ids="";
 		    	  var checkedNodes=vo.$refs.tree.getCheckedNodes(true);
 		    	    for(var i=0;i<checkedNodes.length;i++){
 		    		  vo.menu_ids +=checkedNodes[i].menu_id+',';
 		    	   }
 		    	  var url="${ctx}/roleToMenu/saveRoleToMenu";
 		    	  var formData ={    "token":vo.token,
 		    			  			 "ROLE_ID": vo.addMenuRoleId,
			 		    			 "MENU_IDS":vo.menu_ids, 
			 		    			 "PARENT_GUID":vo.addMenuPARENT_GUID
			        			};
 		    	 vo.$http.post(url,JSON.stringify(formData) ).then(function(res){ 
 				        	
 							  		if(res.ok){
 							  			vo.$message({
 							  	          message: '保存成功！',
 							  	       	  duration:1000 ,
 							  	          type: 'success',
 							              onClose:function(){
 							            	 vo._data.dialogAddMenu = false;
 							            	 vo.bindTableData();
 							              } 
 							  	        });
 							  		}else{
 							  			 vo.$message.error('保存失败！');
 							  		}
 						});   
 		      },
 		      handleCurrentRow:function(row) {//给选中行赋值
 		          vo.currentRow = row;
 		        },
 				judeCurrentRowIsNull:function(msg) {//判断是否选中行
 			          if(vo.currentRow==null){
 			        	  vo.$message({
 			        		 message: '请选择一条角色数据进行'+msg+"!",
 				              type: 'warning' 
 				            });
 			        	  return false;
 			          }else{
 			        	  return true;
 			          }
 			        	  
 		        }
        }
    });

    Hp.createNew(vo);//关联助手类和vo对象
</script>
</html>
