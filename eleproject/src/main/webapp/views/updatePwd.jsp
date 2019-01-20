<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/resource/static/static.jsp"></jsp:include>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=yes, initial-scale=1.0, maximum-scale=2.0, minimum-scale=0.5">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="renderer" content="webkit">
    <title>修改密码</title>
    <link rel="stylesheet" href="${ctx}/resource/common/font-awesome.css"/>
    <link rel="stylesheet" href="${ctx}/resource/common/element.css"/>
    <link rel="stylesheet" href="${ctx}/resource/css/base.css"/>
    <script src="${ctx}/resource/common/vue.js"></script>
    <script src="${ctx}/resource/common/vue-resource.min.js"></script>
    <script src="${ctx}/resource/common/element.js"></script>
    <script src="${ctx}/resource/common/store.modern.min.js"></script>
    <script src="${ctx}/resource/js/base.js"></script>
    <style type="text/css">
 		.el-dialog__body{
 			padding:10px 20px 10px 10px;
 		}
    </style>
</head>
<body>
<div id="app" class="v__addAnimationBg">
     <el-dialog title=""  width="30%"  visible :modal-append-to-body="false" :close-on-click-modal="false" :show-close="false">
         	<el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" :label-width="formLabelWidth" size="mini">
         		  <el-form-item label="旧 密 码" prop="oldPass">
				    <el-input type="password" v-model="ruleForm.oldPass" auto-complete="off"></el-input>
				  </el-form-item>
				  <el-form-item label="新 密 码" prop="pass">
				    <el-input type="password" v-model="ruleForm.pass" auto-complete="off"></el-input>
				  </el-form-item>
				  <el-form-item label="确认密码" prop="checkPass">
				    <el-input type="password" v-model="ruleForm.checkPass" auto-complete="off"></el-input>
				  </el-form-item>
				  <el-form-item style="text-align: right;">
				  	<el-button @click="resetForm('ruleForm')">重置</el-button>
				  	<el-button type="primary" @click="submitForm('ruleForm')">确认</el-button>
				  </el-form-item>
			</el-form>
	  </el-dialog>
</div>
</body>
<script type="text/javascript">
	
	 Vue.http.options.emulateJSON = true;
    var vo = new Vue({
        el: "#app",
        data: function () {
        	 var validatePass = (rule, value, callback) => {
        	        if (value === '') {
        	          callback(new Error('请输入新密码！'));
        	        } else if (value.length <6) {
        	          callback(new Error('密码至少为6位数！'));
        	        } else {
        	          if (this.ruleForm.checkPass !== '') {
        	            this.$refs.ruleForm.validateField('checkPass');
        	          }
        	          callback();
        	        }
        	      };
        	      var validatePass2 = (rule, value, callback) => {
        	        if (value === '') {
        	          callback(new Error('请再次输入新密码！'));
        	        } else if (value !== this.ruleForm.pass) {
        	          callback(new Error('两次输入密码不一致！'));
        	        } else {
        	          callback();
        	        }
        	      };
            return {
            	
                token:store.get("token"),
				v__tableDockHeight:0,
				formLabelWidth:'80px',
			    ruleForm: {
			    	  oldPass:'',
			          pass: '',
			          checkPass: '',
			          USERNAME: ''
		        },
		        rules: {
		        	
		        	oldPass: [
			              { required: true, message: '请输入旧密码！', trigger: 'blur' }
			            ],
		            pass: [
		              { required: true, message: '请输入新密码！',validator: validatePass, trigger: 'blur' }
		            ],
		            checkPass: [
		              { required: true, message: '请再次输入新密码！',validator: validatePass2, trigger: 'blur' }
		            ]
		          }
            }
        },
        mounted: function () {
            //页面装载完毕后加载表格数据
            this.$nextTick(function() {
			  })

        },
        methods: {
        	resetForm: function(formName) {
		        this.$refs[formName].resetFields();
		      },
			submitForm: function(formName) {
		        this.$refs[formName].validate(function(valid){
		          if (valid) {
		        	  vo.$http.get("${ctx}/emp/checkPwd",
	 							 {   params: {
	 								 "token":vo.token,
	 								 "oldPass":vo.ruleForm.oldPass}
	 	 						    }).then(function(res){ 
		 						    	if(res.bodyText=="0"){
		 						    		vo.$message.warning('旧密码不正确，请重新输入！');
		 						    	}else{
		 						    		vo.updatePwd();
		 						    	}
	 					}); 
		              
		          } else {
		            return false;
		          }
		        });
		      },
		      updatePwd: function(){//修改密码
		    		 vo.$http.get("${ctx}/emp/updatePwd",
 							 {   params: {
 								 "token":vo.token,
 								 "USER_PWD":vo.ruleForm.pass}
 	 						    }).then(function(res){ 
	 						    	if(res.bodyText=="1"){
	 						    		 vo.$message({
								              message: '密码修改成功！',
								              duration:1000,
								              type: 'success'
								            });
	 						    	}else{
	 						    		vo.$message.error('密码修改失败，请刷新后重试！');
	 						    	}
 						  			vo.resetForm('ruleForm');
 				}); 
		      }
	      }
    });

    Hp.createNew(vo);
</script>