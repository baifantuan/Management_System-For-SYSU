<script setup>
import { ref, onMounted} from 'vue';
import { queryAllApi, addApi, queryByIdApi, updateApi, deleteByIdApi } from '@/api/dept';
import { ElMessage, ElMessageBox } from 'element-plus';

onMounted(() => {
    search();
  }
)

const search = async () => {
  const result = await queryAllApi();
  if(result.code){
    deptList.value = result.data;
  }
}

const addDept = () => {
  dialogFormVisible.value = true;
  formTitle.value = '新增部门';
  dept.value = {name: ''};
  //重置表单校验规则和提示信息
  if (deptFormRef.value) {
    deptFormRef.value.resetFields();
  }
}

const deptList = ref([]);
const dialogFormVisible = ref(false);
const dept = ref({name:''});
const formTitle = ref('');

const save = async () => {
  if(!deptFormRef.value)
    return;
  deptFormRef.value.validate(async (valid) => {
    if(valid){

      let result;
      if(dept.value.id) {
        result = await updateApi(dept.value);
      }else{
        result = await addApi(dept.value);
      }

      if(result.code){
        ElMessage.success('操作成功');
        dialogFormVisible.value = false;
        search();
      }else{
        ElMessage.error(result.msg);
      }
    }else{
      ElMessage.error('表单填写不符合规范！');
    }
  })

}

const rules = ref({
  name: [
    { required: true, message: '部门名称必填', trigger: 'blur' },
    { min: 2, max: 10, message: '部门名称长度在2-10字之间', trigger: 'blur' },
  ]
})

const deptFormRef = ref();

const edit = async (id) => {
  formTitle.value = '修改部门';
  if (deptFormRef.value) {
    deptFormRef.value.resetFields();
  }

  const result = await queryByIdApi(id);
  if(result.code){
    dialogFormVisible.value = true;
    dept.value = result.data;
  }
}

const delById = async (id) => {
  ElMessageBox.confirm(
    '此操作将永久删除该部门信息，是否继续?',
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


</script>

<template>
  <h1>部门管理</h1>
  <div class="container">
    <el-button type="primary" round @click="addDept">+ 新增部门</el-button>
  </div>
  

  <div class="container"> 
    <el-table :data="deptList" border style="width: 100%">
      <el-table-column type="index" label="序号" width="100" align="center" />
      <el-table-column prop="name" label="部门名称" width="260" align="center" />
      <el-table-column prop="createTime" label="创建时间" width="300" align="center" />
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button type="primary" round size="small" @click="edit(scope.row.id)"><el-icon><EditPen /></el-icon> 编辑</el-button>
          <el-button type="danger" round size="small" @click="delById(scope.row.id)"><el-icon><Delete /></el-icon> 删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <el-dialog v-model="dialogFormVisible" :title="formTitle" width="500">
    <el-form ref="deptFormRef" :model="dept" :rules="rules">
      <el-form-item label="部门名称" label-width="80px" prop="name">
        <el-input v-model="dept.name"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="save">确定</el-button>
      </div>
    </template>
  </el-dialog>


</template>

<style scoped>
.container{
  margin: 20px 0px;
}
</style>
