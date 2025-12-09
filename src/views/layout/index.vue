<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus';
import { useRouter } from 'vue-router';

const currentEmployee = ref('');
const router = useRouter();

onMounted(() => {
  const loginUser = localStorage.getItem('loginUser') ? JSON.parse(localStorage.getItem('loginUser')) : null;
  if (loginUser && loginUser.name) {
    currentEmployee.value = loginUser.name;
  }
})


const logout = () => {
  ElMessageBox.confirm(
    '您确定要退出登录吗？',
    '提示',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(() => {
      ElMessage.success('退出登录成功');
      localStorage.removeItem('loginUser');
      router.push('/login');
    }
  ).catch(() => {
      ElMessage.info('您已取消退出登录');
  })
}

</script>

<template>
  <div class="common-layout">
    <el-container>
      <!-- Header 区域 -->
      <el-header class="header">
        <span class="title">中山大学职工学员教务信息管理系统</span>
        <span class="right_tool">
          <a href="">
            <el-icon><EditPen /></el-icon> 修改密码 &nbsp;&nbsp;&nbsp; |  &nbsp;&nbsp;&nbsp;
          </a>
          <a href="javascript:void(0)" @click="logout">
            <el-icon><SwitchButton /></el-icon> 退出登录 【当前员工:{{ currentEmployee }}】
          </a>
        </span>
      </el-header>
      
      <el-container>
        <!-- 左侧菜单 -->
        <el-aside width="260px" class="aside">
            <!-- 左侧菜单栏 -->
            <el-menu router="true">
              <!-- 首页菜单 -->
              <el-menu-item index="/index">
                <el-icon><Promotion /></el-icon> 首页
              </el-menu-item>
              
              <!-- 班级管理菜单 -->
              <el-sub-menu index="/manage">
                <template #title>
                  <el-icon><HomeFilled /></el-icon>班级学员管理
                </template>
                <el-menu-item index="/clazz">
                  <el-icon><House /></el-icon>班级管理
                </el-menu-item>
                <el-menu-item index="/stu">
                  <el-icon><UserFilled /></el-icon>学员管理
                </el-menu-item>
              </el-sub-menu>
              
              <!-- 系统信息管理 -->
              <el-sub-menu index="/system">
                <template #title>
                  <el-icon><Tools /></el-icon>系统信息管理
                </template>
                <el-menu-item index="/dept">
                  <el-icon><Menu /></el-icon>部门管理
                </el-menu-item>
                <el-menu-item index="/emp">
                  <el-icon><Avatar /></el-icon>员工管理
                </el-menu-item>
              </el-sub-menu>
            
              <!-- 数据统计管理 -->
              <el-sub-menu index="/report">
                <template #title>
                  <el-icon><Histogram /></el-icon>数据统计管理
                </template>
                <el-menu-item index="/empReport">
                  <el-icon><InfoFilled /></el-icon>员工信息统计
                </el-menu-item>
                <el-menu-item index="/stuReport">
                  <el-icon><HelpFilled /></el-icon>学员信息统计
                </el-menu-item>
                <el-menu-item index="/log">
                  <el-icon><Document /></el-icon>日志信息统计
                </el-menu-item>
              </el-sub-menu>
            </el-menu>

        </el-aside>
        
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
      
    </el-container>
  </div>
</template>

<style scoped>
.header {
  background-image: linear-gradient(to right, #007d70, #007fa4, #00aaa0, #00d072, #ebd912);
}

.title {
  color: white;
  font-size: 40px;
  font-family: 楷体;
  line-height: 60px;
  font-weight: bolder;
}

.right_tool{
  float: right;
  line-height: 60px;
}

a {
  color: white;
  text-decoration: none;
}

.aside {
  width: 220px;
  border-right: 1px solid #ccc;
  height: 730px;
}
</style>
