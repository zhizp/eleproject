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
    <title>菜单</title>
    <link rel="stylesheet" href="${ctx}/common/font-awesome.css"/>
    <link rel="stylesheet" href="${ctx}/common/element.css"/>
    <link rel="stylesheet" href="${ctx}/css/base.css"/>
    <script src="${ctx}/common/vue.js"></script>
    <script src="${ctx}/common/vue-resource.min.js"></script>
    <script src="${ctx}/common/element.js"></script>
    <script src="${ctx}/common/store.modern.min.js"></script>
    <script src="${ctx}/js/base.js"></script>
 
    <style>
 
		.avatar-uploader .el-upload {
		    border: 1px dashed #d9d9d9;
		    border-radius: 6px;
		    cursor: pointer;
		    position: relative;
		    overflow: hidden;
		  }
		  .avatar-uploader .el-upload:hover {
		    border-color: #20a0ff;
		  }
		  .avatar-uploader-icon {
		    font-size: 28px;
		    color: #8c939d;
		    width: 78px;
		    height: 78px;
		    line-height: 78px;
		    text-align: center;
		  }
		  .avatar {
		    width: 78px;
		    height: 78px;
		    display: block;
		  }
</style>
</head>
<body style="overflow:hidden;">
<div id="app"  class="vo v__addAnimationBg">
    <el-row style="padding-bottom:5px;">
        <el-col :span="24">
		   		<el-button  type="primary" size="small" icon="el-icon-plus" @click="HandleAdd"  >新增</el-button>
		   		<el-button  type="primary" size="small" icon="el-icon-edit" @click="handleEdit" >编辑</el-button>
		   		<el-button  type="primary" size="small" icon="el-icon-delete" @click="handleDelete" >删除</el-button>
        </el-col>
    </el-row>
    <el-row style="position: absolute;top: 35px;bottom: 0;width: 100%">
        
        <!-- 树形菜单 begin -->
        <el-col :span="6" style="height: 100%; padding-right: 10px; border: 1px solid #eee;">
            <el-tree :data="treeData"  node-key="id" ref="tree" highlight-current  v-loading.fullscreen.lock="fullscreenLoading"
                     :props="defaultProps" style="height: 100%; overflow: auto" @node-click="handleNodeClick">
            </el-tree>
        </el-col>
        <!-- 树形菜单end -->
        
        <!-- 列表 begin -->
         <el-col :span="18" style="height: 100%; position: relative;">
	   		<el-row class="v__tableDock" style="position: absolute;top: 0px;bottom: 30px;width: 100%">
		        <el-col :span="24">
		            <el-table :data="tableData" :height="v__tableDockHeight" stripe border style="width: 100%;" 
		            	v-loading.fullscreen.lock="tableLoading"  highlight-current-row 
				              @row-click="handleCurrentRow">
		                  <el-table-column  type="index" label="序号" width="80" align="center"></el-table-column>
						  <el-table-column prop="name" label="菜单名称" min-width="200"> </el-table-column>
						  <el-table-column prop="url" label="URL" min-width="200"> </el-table-column>
						  <el-table-column prop="pname" label="父级菜单" min-width="200"> </el-table-column>
						  <el-table-column prop="remarks" label="菜单描述" min-width="300"> </el-table-column>
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
        </el-col> 
          <!-- 列表end -->	  
			<!-- 修改 begin -->
			    <el-dialog ref="dlgEdit" title="编辑菜单" size="small" :visible.sync="dialogEditFormVisible" 
					:modal-append-to-body="false" :close-on-click-modal="false">
					<el-form :model="editForm" :rules="rules" ref="editForm" >
					   	<el-form-item label="菜单名称" :label-width="formLabelWidth" prop="name" style="margin-bottom:15px;">
					      <el-input size="small" v-model="editForm.name" auto-complete="off" ></el-input>
					    </el-form-item>
					    <el-form-item label="URL" :label-width="formLabelWidth" prop="url">
					      <el-input size="small" v-model="editForm.url" auto-complete="off"></el-input>
					    </el-form-item>
					    <el-form-item label="父级菜单" :label-width="formLabelWidth" >
					      <el-input size="small" v-model="editForm.parentname" disabled  >
					       <el-input v-model="editForm.parentid" v-show="false" >
					      </el-input>
					    </el-form-item>
					    <el-form-item label="排序号" :label-width="formLabelWidth" prop="displayorder"  style="margin-bottom:15px;">
					      <el-input size="small" v-model="editForm.displayorder" auto-complete="off"></el-input>
					    </el-form-item>
					    <el-form-item label="菜单描述" :label-width="formLabelWidth"  prop="remarks">
					      <el-input  size="small" type="textarea"  :rows="3" v-model="editForm.remarks" auto-complete="off"></el-input>
					    </el-form-item>
						<%--<el-form-item label="菜单图标" :label-width="formLabelWidth"  prop="MENUICON">--%>
						<%--<el-upload v-model="editForm.MENUICON" class="avatar-uploader" :action="action" name="file" ref="file"--%>
						<%--:show-file-list="false" :on-success="handleAvatarSuccessUpdate" :before-upload="beforeAvatarUpload" >--%>
						<%--<img v-if="imageUrlUpdate" :src="imgpath+imageUrlUpdate" class="avatar" >--%>
						<%--<i v-else class="el-icon-plus avatar-uploader-icon"></i>--%>
						<%--</el-upload>--%>
						<%--</el-form-item>--%>
			
					  </el-form>
					  <div slot="footer" class="dialog-footer" style="margin-left: 36%;" >
					    <el-button size="small" @click="dialogEditFormVisible = false">取 消</el-button>
					    <el-button type="primary"size="small"  @click="updateMenu('editForm')">确认</el-button>
					  </div>
				  </el-dialog>
            <!-- 修改end -->	
            
            <!-- 新增 begin -->	  
		  <el-dialog ref="dlgAdd" title="新增菜单" size="small" :visible.sync="dialogAddFormVisible" :modal-append-to-body="false" :close-on-click-modal="false">
		  <el-form :model="form" :rules="rules" ref="form" >
		    <el-form-item label="菜单名称" :label-width="formLabelWidth" prop="name" style="margin-bottom:15px;">
		      <el-input  size="small" v-model="form.name" auto-complete="off" ></el-input>
		    </el-form-item>
		    <el-form-item label="URL" :label-width="formLabelWidth" prop="url" >
		      <el-input  size="small" v-model="form.url" auto-complete="off"></el-input>
		    </el-form-item>
		    <el-form-item label="父级菜单" :label-width="formLabelWidth" >
		      <el-input  size="small" v-model="form.parentname" disabled  >
		       <el-input v-model="form.parentid" v-show="false" >
		      </el-input>
		    </el-form-item>
		    <el-form-item label="排序号" :label-width="formLabelWidth" prop="displayorder"  style="margin-bottom:15px;">
		      <el-input  size="small" v-model.number="form.displayorder" auto-complete="off"></el-input>
		    </el-form-item>
		    <el-form-item label="菜单描述" :label-width="formLabelWidth"  prop="remarks" >
		      <el-input  type="textarea"  :rows="3" v-model="form.remarks" auto-complete="off" ></el-input>
		    </el-form-item>
			  <%--<el-form-item label="菜单图标" :label-width="formLabelWidth"  prop="MENUICON">--%>
			  <%--<el-upload v-model="form.MENUICON" class="avatar-uploader" :action="action" name="file" ref="file"--%>
			  <%--:show-file-list="false" :on-success="handleAvatarSuccessAdd" :before-upload="beforeAvatarUpload" >--%>
			  <%--<img v-if="imageUrlAdd" :src="imgpath+imageUrlAdd" class="avatar" >--%>
			  <%--<i v-else class="el-icon-plus avatar-uploader-icon"></i>--%>
			  <%--</el-upload>--%>
			  <%--</el-form-item>--%>
		  </el-form>
		  <div slot="footer" class="dialog-footer" >
		    <el-button size="small" @click="dialogAddFormVisible = false">取 消</el-button>
		    <el-button type="primary" size="small" @click="HandleSave('form')">确认</el-button>
		  </div>
		</el-dialog>
	<!-- 新增 end -->	

    </el-row>
</div>
</body>
<script>
    Vue.http.options.emulateJSON = true;
    var vo = new Vue({
        el: "#app",
        data: function () {
            return {
            	
              token:store.get("token"),
              defaultProps: {children: 'children', label: 'label'},
              treeData: [],
              tableData : [],
              v__tableDockHeight: 0,
              dialogAddFormVisible: false,
		      dialogEditFormVisible: false,
   			  fullscreenLoading: true,
   			  tableLoading: true,
   			  formLabelWidth: '100px',
   			  parentroles: [  ],
   			  addXjParentID:"0",//标记新增菜单的parentID
   			  addXjParentName:"无",//标记新增菜单的parentname
   			  imageUrlAdd: '',//新增上传图片
   			  imageUrlUpdate: '',//编辑上传图片
   			  image:"",
   			  imgpath:"${ctx}/resources/upload/menu/",
   			  action:"${ctx}/menuController/uploadMenuicon?token="+store.get('token'),//"https://jsonplaceholder.typicode.com/posts/",//
   			  currentRow:null,
   			  currentRowIdTree:"",//树当前行id，用于查询表格数据
   			  form: {
   		        	name: '',
   		        	url: '',
   		        	parentname:'',
   		        	parentid: '',
   		        	displayorder:0,
   		        	remarks:'',
   		        	menuicon:''
   		        },
   		        editForm: {
   		        	name: '',
   		        	url: '',
   		        	parentname:'',
   		        	parentid: '',
   		        	displayorder:0,
   		        	remarks:'',
   		        	menuicon:''
   		        },
		        page:{
		        	total:0 ,
		        	pagesize:20,
		        	currentpage:1
		        },
   		        rules: {
   		        	name: [
   		 	              { required: true, message: '请输入菜单名称'}
   		 	             ],
   		 	        remarks: [
   	 	              		{ required: true, message: '请输入菜单描述'}
   	 	            	]  ,
   	 	           	displayorder: [ 	    
   	 	        	   			{ required: true, message: '排序号不能为空'} /* ,
   	 	                       	{ type: 'number', message: '排序号必须为数字值'}  */
   	 			 	            ]  
 		 	        }
            }
        },
        mounted: function () {
            //页面装载完毕后加载表格数据
            this.$nextTick(function() {
			    // 保证vo.$el已经插入文档
                vo.bindTreeData();
                vo.bindTableData();
			  })

        },
        methods: {
         resetForm:function(formName) {// 重置表单
        		if(vo.$refs.hasOwnProperty('form'))//判断是否包括form表单
  	            	vo.$refs.form.resetFields();
  	          },
         bindTreeData: function () {
            	 vo.$http.get("${ctx}/menuController/queryMenuTree",
							 {  /*  params: {"token":vo.token} */
	 						    }).then(function(res){ 
					 vo.treeData=res.body;
					 vo.fullscreenLoading=false; 
			});  
		},
 		 bindTableData:function(){//表格数据
 				
				  vo.$http.get("${ctx}/menuController/getMenuPage",
						 {   params: {  
								/* "token":vo.token, */
						  		"pagesize":vo.page.pagesize,
						        "currentpage":vo.page.currentpage,
						        "menu_id":vo.currentRowIdTree
						    }}).then(function(res){ 
						    	
						 vo.page.currentpage=res.body.currentpage;
					 	 vo.page.total=res.body.total;
						 vo.tableData=res.body.list; 
						 vo.tableLoading=false;
					
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
		handleCurrentRow:function(row) {//点击表格行
	    	//给选中行赋值
		       vo.currentRowId = row.menu_id;
		       //给“新增”的父级菜单字段赋值 
	    	   vo.addXjParentId=row.menu_id;
	    	   vo.addXjParentName=row.name;
 		        },
	    handleNodeClick:function(data) {// 单击树节点
	    	//给树选中行赋值
	       vo.currentRowIdTree = data.menu_id;
	    	//给表格选中行赋值
	       vo.currentRowId = data.menu_id;
	       //给“新增”的父级菜单字段赋值 
    	   vo.addXjParentId=data.menu_id;
    	   vo.addXjParentName=data.label;
    	   //刷新表格
    	   vo.bindTableData();
	      },
	   HandleAdd:function(){//添加下级菜单
		   
	    	  vo.dialogAddFormVisible = true;
	    	  vo.resetForm();	// 重置表单
	    	  vo.form.parentid=vo.addXjParentId;
	    	  vo.form.parentname=vo.addXjParentName;
	    	  vo.imageUrlAdd ="";
			},
		HandleSave:function(form){
			 vo.$refs[form].validate(function(valid) {
		          if (valid) {
		        	vo.form["token"] = vo.token; 
					var formData = JSON.stringify(vo.form);
					  
					var url="${ctx}/menuController/saveMenu";
				  	vo.$http.post(url,formData ).then(function(res){  
				   
				  		if(res.body.rt){
				  			 vo.$message({
					              message: '保存成功！',
					              duration:1000,
					              type: 'success',
					              onClose:function(){
 						            	 vo._data.dialogAddFormVisible = false;
 						            	 vo._data.currentRowId = null;//当前选中行置空
 						            	 vo.bindTreeData();
 						            	 vo.bindTableData();
 						            	 vo.addXjParentId="0";
 						            	 vo.addXjParentName="无";
					              } 
					            });
				  		}else{
				  			vo.$message({
					              message: '保存失败！',
					              duration:1000,
					              type: 'error'
					            });
				  		}
						    	 
					});   
		          } else {
		            return false;
		          }
		        });
			
		},
		 handleEdit:function(index, row) {
			 if( vo.judeCurrentRowIsNull('编辑')){
			 	 vo.dialogEditFormVisible = true;
				 vo.$http.get("${ctx}/menuController/getOneMenuList",
						 {   params: { "token":vo.token,"menu_id":vo.currentRowId}
					    }).then(function(res){  
					    	vo.editForm=res.body;
					    	vo.imageUrlUpdate =res.body.menuicon;
					    	if(res.body.parentid=="0"){
						    	  vo.editForm.parentid="0";
						    	  vo.editForm.parentname="无";
						    	  //vo.editForm.DISPLAYORDER=parseInt(res.body.DISPLAYORDER);
					    	}
					    	
					
				});  
			 }
	      },
	      updateMenu:function(editForm){
				 vo.$refs[editForm].validate(function(valid) {
			          if (valid) {
			        	vo.editForm["token"] = vo.token;
			        	vo.editForm["menu_id"] = vo.currentRowId;
						var formData = JSON.stringify(vo.editForm);
						  
						var url="${ctx}/menuController/updateMenu";
					  	vo.$http.post(url,formData ).then(function(res){  
					   
					  		if(res.ok){
					  			 vo.$message({
						              message: '修改成功！',
						              duration:1000,
						              type: 'success',
						              onClose:function(){
	 						            	 vo._data.dialogEditFormVisible = false;
	 						            	 vo._data.currentRowId = null;//当前选中行置空
	 						            	 vo.addXjParentId="0";
	 						            	 vo.addXjParentName="无";
	 						            	 vo.bindTreeData();
	 						            	 vo.bindTableData();
						              } 
						            });
					  		}else{
					  			vo.$message({
						              message: '修改失败！',
						              duration:1000,
						              type: 'success',
						            });
					  		}
							    	 
						});   
			          } else {
			            return false;
			          }
			        });
				
			},
			  handleDelete:function() {
				  if( vo.judeCurrentRowIsNull('删除')){
		    	  //判断是否有子菜单，若有则先删除子菜单
		    	   vo.$http.get("${ctx}/menuController/getMenuList",
								 {   params: { "token":vo.token,"PARENTID":vo.currentRowId }
				        }).then(function(res){ 
				        	 
							  	  	if(res.body.length>0){
							  			 vo.$message({
								              message: '请先删除子菜单！',
								              duration:2000,
								              type: 'warning' 
								            });
							  			
							  		}else{
							  			vo.$confirm('确定删除该该菜单?', '提示', {
								              confirmButtonText: '确定',
								              cancelButtonText: '取消',
								              type: 'warning'
								            }).then(function(){
								            	 vo.$http.get("${ctx}/menuController/deleteMenu",
														 {   params: { "token":vo.token,"menu_id":vo.currentRowId }
										        }).then(function(res){ 
										        	
													  		if(res.ok){
													  			 vo.$message({
														              message: '删除成功！',
														              duration:1000,
														              type: 'success' ,
														              onClose:function(){
														            	     vo._data.currentRowId = null;//当前选中行置空
									 						            	 vo.bindTreeData();
									 						            	 vo.bindTableData();
									 						            	 vo.addXjParentId="0";
									 						            	 vo.addXjParentName="无";
														              } 
														            });
													  			
													  		}else{
													  			vo.$message({
														              message: '删除失败！',
														              duration:1000,
														              type: 'success' 
														            });
													  		}
												});  
										        
								            }).catch(function() {
								            });
							  		} 
							  		
						});  
				  }
		       
		      },
		      handleAvatarSuccessAdd:function(res, file) {//菜单图片添加成功
			    	 vo.form.MENUICON=res.fileName;
			         vo.imageUrlAdd =res.fileName;
			        },
		      handleAvatarSuccessUpdate:function(res, file) {//菜单图片修改成功
		    	 vo.editForm.MENUICON=res.fileName; 
		         vo.imageUrlUpdate =res.fileName;
		        },
	        beforeAvatarUpload:function(file) {//上传图片验证
	        	vo.image=file;
	          const isJPG = file.type;// === 'image/jpeg';//image/png
	          const isLt2M = file.size / 1024 / 1024 < 2;
	
	          if (isJPG != "image/png" && isJPG != "image/jpeg") {
	            vo.$message.error('上传头像图片只能是 JPG/PNG 格式!');
	          }
	          if (!isLt2M) {
	            vo.$message.error('上传头像图片大小不能超过 2MB!');
	          }
	          return isJPG && isLt2M;
	        },
 			judeCurrentRowIsNull:function(msg) {//判断是否选中行
 			          if(vo.currentRowId==null){
 			        	  vo.$message({
 				              message: '请选择一条菜单数据进行'+msg+"!",
 				              type: 'warning' 
 				            });
 			        	  return false;
 			          }else{
 			        	  return true;
 			          }
 			        	  
 		        }
	      }
    });

    Hp.createNew(vo);
</script>
</html>
