<template>
  <div class="sys-admin">
    <div class="admin-input" align="center">
      <el-input class="sys-admin-input" v-model="keywords" clearable
                placeholder="请输入用户名" prefix-icon="el-icon-search"></el-input>
      <el-button type="primary" icon="el-icon-search" @click="searchAdmin">搜索</el-button>
    </div>
    <div class="admin-card">
      <el-card class="box-card" v-for="(admin, index) in admins" :key="index" shadow="hover">
        <template #header>
          <div class="card-header" align="center">
            <span>{{admin.name}}</span>
            <el-button class="button" type="text" icon="el-icon-delete"
                       @click="deleteAdmin(admin)"></el-button>
          </div>
        </template>
        <div class="user-face" align="center">
          <div class="img-container">
            <!-- :src="admin.userFace" -->
            <img src="../../assets/img/DNA.png" :alt="admin.name" :title="admin.name">
          </div>
        </div>
        <div class="user-list" align="left">
          <div class="user-item">用户名: {{admin.name}}</div>
          <div class="user-item">手机号码: {{admin.phone}}</div>
          <div class="user-item">电话号码: {{admin.telephone}}</div>
          <div class="user-item">地址: {{admin.address}}</div>
          <div class="user-item">用户状态:
            <el-switch v-model="admin.enabled" @change="enabledChnage(admin)"
                       active-color="#13ce66" inactive-color="#ff4949"
                       active-text="启用" inactive-text="禁用"></el-switch></div>
          <div class="user-item">用户角色:
            <el-tag class="user-role" type="success" :key="indexj" size="small"
                    v-for="(role, indexj) in admin.roles">{{role.nameZh}}</el-tag>
            <el-popover placement="right" title="角色列表" :width="200"
                        trigger="click" @show="showPop(admin)" @hide="updateRole(admin)">
              <el-select v-model="selectRoles" placeholder="请选择" multiple>
                <el-option v-for="(role, index) in roles" :key="index"
                           :label="role.nameZh" :value="role.id"></el-option>
              </el-select>
              <template #reference>
                <el-button type="text" icon="el-icon-more"></el-button>
              </template>
            </el-popover>
          </div>
          <div class="user-item" v-if="admin.remark != null">备注: {{admin.remark}}</div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
export default {
  name: "SysAdmin",
  data() {
    return {
      admins: [],
      keywords: "",
      roles: [],
      selectRoles: [],
    }
  },
  methods: {
    searchAdmin() {
      this.initAdmins();
    },
    enabledChnage(admin) {
      delete admin.authorities;
      console.log(admin);
      this.putRequest("/admin/", admin).then(resp => {
        if (resp) {
          this.initAdmins();
        }
      });
    },
    showPop(admin) {
      let roles = admin.roles;
      this.selectRoles = [];
      roles.forEach(role => {
        this.selectRoles.push(role.id);
      });
      this.initRoles();
    },
    /**
    * @Description 发送请求
    * @Return
    * @Author 脱氧核糖
    * @Date 2021/9/9 14:36
    */
    initAdmins() {
      this.getRequest("/admin/?keywords=" + this.keywords).then(resp => {
        if (resp) {
          this.admins = resp;
        }
      });
    },
    deleteAdmin(admin) {
      this.$confirm("此操作将永久删除【" + admin.name + "】操作员, 是否继续？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.deleteRequest("/admin/" + admin.id).then(resp => {
          if (resp) {
            this.initAdmins();
          }
        })
      }).catch(() => {
        this.$message({
          type: "info",
          message: "已取消操作"
        });
      });
    },
    initRoles() {
      this.getRequest("/role/").then(resp => {
        if (resp) {
          this.roles = resp;
        }
      });
    },
    updateRole(admin) {
      //判断角色信息是否改变
      let flag = false;
      let roles = [];
      Object.assign(roles, admin.roles);
      if (roles.length != this.selectRoles.length) {
        flag = true;
      } else {
        for (let i = 0; i < roles.length; i++) {
          let role = roles[i];
          for (let j = 0; j < this.selectRoles.length; j++) {
            let sr = this.selectRoles[j];
            if (role.id == sr) {
              roles.splice(i, 1);
              i--;
              break;
            }
          }
        }
        if (roles.length != 0)
          flag = true;
      }
      if (flag) {
        let url = "/admin/role?adminId=" + admin.id;
        this.selectRoles.forEach(sr => {
          url += "&rids=" + sr;
        });
        this.putRequest(url).then(resp => {
          if (resp) {
            this.initAdmins();
          }
        });
      }
    },
  },
  mounted() {
    this.initAdmins();
  }
}
</script>

<style scoped>
.admin-input {
  margin-top: 10px;
}
.sys-admin-input {
  width: 400px;
  margin-right: 10px;
}
.button {
  float: right;
}

.admin-card {
  margin-left: 45px;
  display: flex;
  margin-top: 20px;
  width: 1200px;
  justify-content: space-around;
}
.box-card {
  width: 270px;
  margin-bottom: 20px;
  margin-right: 20px;
}

.user-face img {
  width: 72px;
  height: 72px;
  border-radius: 72px;
  margin-bottom: 10px;
}

.user-item {
  margin-bottom: 5px;
}

.user-role {
  margin-right: 5px;
  margin-top: 5px;
}
</style>