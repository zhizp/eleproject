/* 获取项目根路径  如： http://localhost:8080/HebZDK */
function getRootPath_dc() {
            var pathName = window.location.pathname.substring(1);
            var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));
            if (webName == "") {
                return window.location.protocol + '//' + window.location.host;
            }
            else {
                return window.location.protocol + '//' + window.location.host + '/' + webName;
            }
        }

/**
 * 合并列函数
 * Auth:hw
 * Date:2018-1-3
 * @param {Object} row
 * @param {Object} column
 * @param {Object} rowIndex
 * @param {Object} columnIndex
 * @param {Object} colIdx  要合并的列索引
 * @param {Object} data 表格全部数据
 * @param {object} columnname 列字段名
 * demo:
 * <el-table :data="tableData" stripe style="width: 100%"  :span-method="objectSpanMethod">
 * methods:{
 *      objectSpanMethod(row, column, rowIndex, columnIndex ) {
 * 		    if(row.columnIndex==1){
 * 			    return elColspan(row,column,rowIndex,columnIndex,1,this.tableData,'prop');//合并第2列(索引从0开始)
 * 		    }
 *      }
 * }
 */
function elColspan(row, column, rowIndex, columnIndex,colIdx,data,columnname){
    if(row.columnIndex==undefined){
        return;
    }
    if(row.columnIndex===colIdx){
        if(row.rowIndex+1==data.length){
            var frontrow = data[row.rowIndex-1];
            if (frontrow==undefined){
                return[1,1];
            }
            if(frontrow[columnname]===row.row[columnname]){
                return[0,0];
            }else {
                return[1,1];
            }
        }
        if(row.rowIndex>0){
            var frontrow = data[row.rowIndex-1];
            if(frontrow[columnname]===row.row[columnname]){
                return[0,0];
            }
        }

        var rowspan=1;
        for(var rowidx=row.rowIndex+1;rowidx<data.length;rowidx++){
            var nextrow = data[rowidx];
            if(row.row[columnname]===nextrow[columnname]){
                rowspan++;
            }else {
                break;
            }
        }

        if(rowspan>=1){
            return{
                rowspan: rowspan,
                colspan: 1
            };
        }else{
            return{
                rowspan: 0,
                colspan: 0
            }
        }
    }
}
// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
Date.prototype.addDays = function(number)
{
    var adjustDate = new Date(this.getTime() + 24*60*60*1000*number)
    //alert("Date" + adjustDate.getFullYear()+"-"+adjustDate.getMonth()+"-"+adjustDate.getDate());
    return adjustDate;
}

function sleep(d){
    for(var t = Date.now();Date.now() - t <= d;);
}
/**
 * 导出--获取所有表格列头数据
 * @param datas
 * @param plabel
 * @returns
 */
 function handleHeadDatas(datas,plabel){//导出--处理表头数据   plabel:上级lable
	   var headDatas=[];
	   for(var i=0;i<datas.length;i++){
		   
		   if(datas[i].label == undefined || datas[i].label =="" ||datas[i].label ==null){
				break;
			}
		   if(datas[i].$children.length<1){//不包含子表头
			    var column={label: plabel+datas[i].label,prop: datas[i].prop};
			    headDatas.push(column);  
		   } else{//含子表头
			   	   var pname=plabel+datas[i].label+ ">" ;
				   handleHeadDatas(datas[i].$children,pname);
			   }
		   } 
	   return headDatas;
}
 /*table标题强制换行*/
 function renderHeader(createElement,column ) {
	  var label = column.label;
      var labelArr = label.split(',');
      if(labelArr.length==2){
    	 return  createElement('span',[createElement('span', [labelArr[0]]),createElement('br'),createElement('span', [labelArr[1]])]);
      }else if(labelArr.length==3){
    	 return  createElement('span',[createElement('span', [labelArr[0]]),createElement('br'),createElement('span', [labelArr[1]])
    		 ,createElement('br'),createElement('span', [labelArr[2]])]);
      }
     
}

/**
 * 合并列函数(分组合并)
 * Auth:hw
 * Date:2018-1-3
 * @param {Object} row
 * @param {Object} column
 * @param {Object} rowIndex
 * @param {Object} columnIndex
 * @param {Object} colIdx  要合并的列索引
 * @param {Object} data 表格全部数据
 * @param {object} columnname 列字段名
 * @param {object} key 根据key分组合并
 * demo:
 * <el-table :data="tableData" stripe style="width: 100%"  :span-method="objectSpanMethod">
 * methods:{
 *      objectSpanMethod(row, column, rowIndex, columnIndex ) {
 * 		    if(row.columnIndex==1){
 * 			    return elColspan(row,column,rowIndex,columnIndex,1,this.tableData,'prop');//合并第2列(索引从0开始)
 * 		    }
 *      }
 * }
 */
function elColspan2(row, column, rowIndex, columnIndex,colIdx,data,columnname,key){
    if(row.columnIndex==undefined){
        return;
    }
    if(row.columnIndex===colIdx){
        if(row.rowIndex+1==data.length){
            var frontrow = data[row.rowIndex-1];
            if (frontrow==undefined){
                return[1,1];
            }
            if(frontrow[columnname]===row.row[columnname] && frontrow[key]==row.row[key]){
                return[0,0];
            }else {
                return[1,1];
            }
        }
        if(row.rowIndex>0){
            var frontrow = data[row.rowIndex-1];
            if(frontrow[columnname]===row.row[columnname] && frontrow[key]===row.row[key]){
                return[0,0];
            }
        }

        var rowspan=1;
        for(var rowidx=row.rowIndex+1;rowidx<data.length;rowidx++){
            var nextrow = data[rowidx];
            if(row.row[columnname]===nextrow[columnname]&&row.row[key]===nextrow[key]){
                rowspan++;
            }else {
                break;
            }
        }

        if(rowspan>=1){
            return{
                rowspan: rowspan,
                colspan: 1
            };
        }else{
            return{
                rowspan: 0,
                colspan: 0
            }
        }
    }
}