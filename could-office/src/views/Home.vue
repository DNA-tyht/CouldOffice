<template>
  <div class="home">
    <el-container>
      <el-header class="home-header">
        <div class="title">CouldOffice-DNA</div>
        <el-dropdown class="user-info" @command="commandHandler">
            <div class="el-dropdown-link">
              <span class="user-name">DNA</span>
              <i><img src="../assets/img/DNA.png"></i>
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="userinfo">个人中心</el-dropdown-item>
                <el-dropdown-item command="setting">设置</el-dropdown-item>
                <el-dropdown-item command="logout">注销</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
      </el-header>
      <el-container>
        <el-aside class="home-aside">
          <el-menu text-color="#ced6df" background-color="#455563" active-text-color="#ffffff" router unique-opened>
            <el-submenu :key="index" v-for="(item, index) in routes" :index="index + ''">
              <template #title>
                <i :class="item.iconCls" style="color: var(--color-one); margin-right: 5px"></i>
                <span style="font-size: 16px;">{{item.name}}</span>
              </template>
              <el-menu-item :index="children.path" v-for="(children, indexj) in item.children" :key="indexj">
                <span>{{children.name}}</span>
              </el-menu-item>
            </el-submenu>
          </el-menu>
        </el-aside>
        <el-main class="home-main">
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "Home",
  computed: {
    routes() {
      return this.$store.state.routes;
    }
  },
  methods: {
    commandHandler(command) {
      if (command === "userinfo") {

      } else if (command === "logout") {
        this.$confirm("此操作将注销登录, 是否继续？", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.postRequest("/logout");
          //清空用户信息
          window.sessionStorage.removeItem("tokenStr");
          window.sessionStorage.removeItem("user");
          this.$store.commit("initRoutes", []);
          this.$router.replace("/login");
        }).catch(() => {
          this.$message({
            type: "info",
            message: "已取消操作"
          });
        });
      } else if (command === "setting") {

      }
    }
  },
}
</script>

<style scoped>
.home-header {
  background: var(--color-background);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 15px;
  box-sizing: border-box;
}
.title {
  color: var(--color-high-text);
  font-size: 24px;
}

.home-header.user-info {
  cursor: pointer;
}
.el-dropdown-link img {
  width: 48px;
  height: 48px;
  border-radius: 24px;
}
.el-dropdown-link {
  height: 60px;
  display:flex;
  flex-direction:row;
  align-items:center;
}
.user-name {
  font-size: 20px;
  color: var(--color-high-text);
  margin-right: 15px;
}

.home-aside {
  background-color: var(--color-background-high);
  width: 200px;
  height: 661px;
}
.home-main {
  height: 661px;
}
</style>