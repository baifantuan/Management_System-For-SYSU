<script setup>
import {ref, watch, onMounted} from 'vue';
import {queryPageApi, addApi, queryByIdApi, updateApi, deleteByIdApi} from '@/api/emp';
import {queryAllApi as queryAllDeptApi} from '@/api/dept';
import { ElMessage, ElMessageBox } from 'element-plus';

const token = ref('');

//职位列表数据
const jobs = ref([{ name: '班主任', value: 1 },{ name: '讲师', value: 2 },{ name: '学工主管', value: 3 },{ name: '教研主管', value: 4 },{ name: '咨询师', value: 5 },{ name: '其他', value: 6 },{ name: '校长', value: 7}])
//性别列表数据
const genders = ref([{ name: '男', value: 1 }, { name: '女', value: 2 }])
//部门列表数据
const depts = ref([])


onMounted(() => {
  search();
  queryAllDepts();
  getToken();
})


const getToken = () => {
  const loginUser = localStorage.getItem('loginUser') ? JSON.parse(localStorage.getItem('loginUser')) : null;
  if(loginUser && loginUser.token){
    token.value = loginUser.token;
  }
}


const queryAllDepts = async () =>{
  const result = await queryAllDeptApi();
  if(result.code){
    depts.value = result.data;
  }
}


const searchEmp = ref({
  name: '',
  gender: '',
  date: [],
  begin: '',
  end: ''
})

const search  = async () =>{
  const result = await queryPageApi(searchEmp.value.name, searchEmp.value.gender, searchEmp.value.begin,
                                    searchEmp.value.end, currentPage.value, pageSize.value);
  if(result.code){
    empList.value = result.data.rows;
    total.value = result.data.total;
  }                    
}

const clear  = () =>{
  searchEmp.value = {name: '', gender: '', date: []};
  search();
}

watch(() => searchEmp.value.date, (newVal, oldVal) => {
  if(newVal.length == 2){
    searchEmp.value.begin = newVal[0];
    searchEmp.value.end = newVal[1];
  }else{
    searchEmp.value.begin = '';
    searchEmp.value.end = '';
  }
})

const empList = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const background = ref(true)
const total = ref(0);

const handleSizeChange = (val) => {
  search();
}
const handleCurrentChange = (val) => {
  search();
}

const employee = ref({
  username: '',
  name: '',
  gender: '',
  phone: '',
  job: '',
  salary: '',
  deptId: '',
  entryDate: '',
  image: '',
  exprList: []
})
 
// 控制弹窗
const dialogVisible = ref(false)
const dialogTitle = ref('')
 
//文件上传
// 图片上传成功后触发
const handleAvatarSuccess = (response) => {
  employee.value.image = response.data;
}
// 文件上传之前触发
const beforeAvatarUpload = (rawFile) => {
  if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
    ElMessage.error('只支持上传图片');
    return false;
  } else if (rawFile.size / 1024 / 1024 > 10) {
    ElMessage.error('只能上传10M以内图片');
    return false;
  }
  return true;
}


const addEmp = () => {
  dialogVisible.value = true;
  dialogTitle.value = '新增员工';
  employee.value = {
    username: '',
    name: '',
    gender: '',
    phone: '',
    job: '',
    salary: '',
    deptId: '',
    entryDate: '',
    image: '',
    exprList: []
  }
  //重置表单校验规则和提示信息
  if (empFormRef.value) {
    empFormRef.value.resetFields();
  }
}


const addExprItem = () => {
  employee.value.exprList.push({
    company: '',
    job: '',
    begin: '',
    end: '',
    exprDate: []
  });
}


const delExprItem = (index) => {
  employee.value.exprList.splice(index, 1);
}



watch(() => employee.value.exprList, (newVal, oldVal) => {
  if(employee.value.exprList && employee.value.exprList.length > 0){
    employee.value.exprList.forEach((expr) => {
      expr.begin = expr.exprDate[0];
      expr.end = expr.exprDate[1];
    })
  }
}, {deep: true})


const save = async () => {
  if(!empFormRef.value)
    return;
  empFormRef.value.validate(async (valid) => {
    if(valid){
      let result;
      if(employee.value.id){
        result = await updateApi(employee.value);
      }else{
        result = await addApi(employee.value);
      }
      if(result.code){
        ElMessage.success('保存成功');
        dialogVisible.value = false;
        search();
      }else{
        ElMessage.error(result.msg);
      }
    }else{
      ElMessage.error('表单填写不符合规范!');
    }
  })
}

const empFormRef = ref()



//表单校验规则
const rules = ref({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 20, message: '用户名长度应在2到20个字符之间', trigger: 'blur' }
  ],
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' },
    { min: 2, max: 10, message: '姓名长度应在2到10个字符之间', trigger: 'blur' }
  ],
  gender: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入有效的手机号', trigger: 'blur' }
  ]
});

const edit =  async (id) => {
  const result = await queryByIdApi(id);
  if(result.code){
    dialogVisible.value = true; 
    dialogTitle.value = '编辑员工';
    employee.value = result.data;
    //处理工作经历的日期范围选择器数据
    if(employee.value.exprList && employee.value.exprList.length > 0){
      employee.value.exprList.forEach((expr) => {
        expr.exprDate = [expr.begin, expr.end];
      })
    }
  }else{
    ElMessage.error(result.msg);
  }
}

const deleteById = (id) => {
  ElMessageBox.confirm(
    '此操作将永久删除该员工信息，是否继续?',
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(async () => {
    const result = await deleteByIdApi(id);
    if(result.code){
      ElMessage.success('删除成功');
      search();
    }else{
      ElMessage.error(result.msg);
    }
  }).catch(() => {
      ElMessage.info('您已取消删除');
  })
}


const selectedEmpIds = ref([])

const handleSelectionChange = (selection) => {
  selectedEmpIds.value = selection.map(item => item.id);
}


const delteByIds = () => {
    ElMessageBox.confirm(
    '此操作将永久删除员工信息，是否继续?',
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(async () => {
    if(selectedEmpIds.value.length == 0){
      ElMessage.info('请先选择要删除的员工');
      return;
    }
    const result = await deleteByIdApi(selectedEmpIds.value);
    if(result.code){
      ElMessage.success('删除成功');
      search();
    }else{
      ElMessage.error(result.msg);
    }
  }).catch(() => {
      ElMessage.info('您已取消删除');
  })
}

</script>

<template>
  <h1>员工管理</h1>
  <div class="container">
    <el-form :inline="true" :model="searchEmp" class="demo-form-inline">
      <el-form-item label="姓名">
        <el-input v-model="searchEmp.name" placeholder="请输入员工姓名" clearable />
      </el-form-item>

      <el-form-item label="性别">
        <el-select v-model="searchEmp.gender" placeholder="请选择" clearable>
          <el-option label="男" value="1" />
          <el-option label="女" value="2" />
        </el-select>
      </el-form-item>

      <el-form-item label="入职时间">
        <el-date-picker
          v-model="searchEmp.date"
          type="daterange"
          range-separator="到"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="search"><el-icon><Search /></el-icon>查询</el-button>
        <el-button type="info" @click="clear"><el-icon><Delete /></el-icon>清空</el-button>
      </el-form-item>
    </el-form>
  </div>

  <div class="container">
    <el-button type="primary" @click="addEmp"><el-icon><Plus /></el-icon> 新增员工</el-button>
    <el-button type="danger" @click="delteByIds"><el-icon><DeleteFilled /></el-icon> 批量删除</el-button>
  </div>

  <div class="container">
    <el-table :data="empList" border style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column prop="name" label="姓名" width="120" align="center"/>
      <el-table-column label="性别" width="120" align="center">
        <template #default="scope">
          {{ scope.row.gender == 1 ? '男' : '女' }}
        </template>
      </el-table-column>
      <el-table-column label="头像" width="120" align="center">
        <template #default="scope">
            <img :src="scope.row.image" height="40px">
        </template>
      </el-table-column>
      <el-table-column prop="deptName" label="所属部门" width="120" align="center"/>
      <el-table-column label="职位" width="120" align="center">
        <template #default="scope">
            <span v-if="scope.row.job == 1">班主任</span>
            <span v-else-if="scope.row.job == 2">讲师</span>
            <span v-else-if="scope.row.job == 3">学工主管</span>
            <span v-else-if="scope.row.job == 4">教研主管</span>
            <span v-else-if="scope.row.job == 5">咨询师</span>
            <span v-else>其他</span>
        </template>
      </el-table-column>  
      <el-table-column prop="entryDate" label="入职日期" width="180" align="center"/>
      <el-table-column prop="updateTime" label="最后操作时间" width="200" align="center"/>
      <el-table-column prop="操作" align="center">
        <template #default="scope">
          <el-button type="primary" round @click="edit(scope.row.id)"><el-icon><EditPen /></el-icon> 编辑</el-button>
          <el-button type="danger" round @click="deleteById(scope.row.id)"><el-icon><Delete /></el-icon> 删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <div class="container">
    <el-pagination
      v-model:current-page="currentPage"
      v-model:page-size="pageSize"
      :page-sizes="[5, 10, 20, 30, 50, 75, 100]"
      :background="background"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>

  <el-dialog v-model="dialogVisible" :title="dialogTitle">
      <el-form :model="employee" :rules="rules" ref="empFormRef" label-width="80px">
        <!-- 基本信息 -->
        <!-- 第一行 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="employee.username" placeholder="请输入员工用户名，2-20个字"></el-input>
            </el-form-item>
          </el-col>
           
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="employee.name" placeholder="请输入员工姓名，2-10个字"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
 
        <!-- 第二行 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-select v-model="employee.gender" placeholder="请选择性别" style="width: 100%;">
                <el-option v-for="g in genders" :key="g.value" :label="g.name" :value="g.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
 
          <el-col :span="12">
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="employee.phone" placeholder="请输入员工手机号"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
 
        <!-- 第三行 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="职位">
              <el-select v-model="employee.job" placeholder="请选择职位" style="width: 100%;">
                <el-option v-for="j in jobs" :key="j.value" :label="j.name" :value="j.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="薪资">
              <el-input v-model="employee.salary" placeholder="请输入员工薪资"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
 
        <!-- 第四行 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="所属部门">
              <el-select v-model="employee.deptId" placeholder="请选择部门" style="width: 100%;">
                <el-option v-for="d in depts" :key="d.id" :label="d.name" :value="d.id"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="入职日期">
              <el-date-picker v-model="employee.entryDate" type="date" style="width: 100%;" placeholder="选择日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
 
        <!-- 第五行 -->
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="头像">
              <el-upload
                class="avatar-uploader"
                action="/api/upload"
                :headers="{'token' : token}"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
                >
                <img v-if="employee.image" :src="employee.image" class="avatar" />
                <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
         
 
        <!-- 工作经历 -->
        <!-- 第六行 -->
        <el-row :gutter="10">
          <el-col :span="24">
            <el-form-item label="工作经历">
              <el-button type="success" size="small" @click="addExprItem">+ 添加工作经历</el-button>
            </el-form-item>
          </el-col>
        </el-row>
         
        <!-- 第七行 ...  工作经历 -->
        <el-row v-for="(expr, index) in employee.exprList" :gutter="3">
          <el-col :span="10">
            <el-form-item size="small" label="时间" label-width="80px">
              <el-date-picker type="daterange" v-model="expr.exprDate" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD" ></el-date-picker>
            </el-form-item>
          </el-col>
 
          <el-col :span="6">
            <el-form-item size="small" label="公司" label-width="60px">
              <el-input placeholder="请输入公司名称" v-model="expr.company"></el-input>
            </el-form-item>
          </el-col>
 
          <el-col :span="6">
            <el-form-item size="small" label="职位" label-width="60px">
              <el-input placeholder="请输入职位" v-model="expr.job"></el-input>
            </el-form-item>
          </el-col>
 
          <el-col :span="2">
            <el-form-item size="small" label-width="0px">
              <el-button type="danger" @click="delExprItem(index)">- 删除</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
       
      <!-- 底部按钮 -->
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="save">保存</el-button>
        </span>
      </template>
  </el-dialog>



</template>

<style scoped>
.container{
  margin: 16px 0px;
}


.avatar {
  height: 40px;
}
.avatar-uploader .avatar {
  width: 78px;
  height: 78px;
  display: block;
}
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}
 
.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}
 
.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 78px;
  height: 78px;
  text-align: center;
  border-radius: 10px;
  /* 添加灰色的虚线边框 */
  border: 1px dashed var(--el-border-color);
}


</style>