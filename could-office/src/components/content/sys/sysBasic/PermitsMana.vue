<template>
  <div class="permits-mana">
    <div class="permits-input">
      <el-input class="add-role1-input" placeholder="请输入角色英文名"
                suffix-icon="el-icon-plus" v-model="role.name">
        <template #prepend>ROLE_</template>
      </el-input>
      <el-input class="add-role2-input" placeholder="请输入角色中文名"
                suffix-icon="el-icon-plus" v-model="role.nameZh" @keydown.enter.native="addRole"></el-input>
      <el-button type="primary" @click="addRole">添加</el-button>
    </div>
    <div align="center">
      <el-collapse class="permits-collapse" v-model="activeNames" @change="changeCollapse" accordion>
        <el-collapse-item :title="role.nameZh" :name="role.id" v-for="(role, index) in roles" :key="index">
          <el-card class="box-card" align="left">
            <template #header>
              <div class="card-header" shadow="hover">
                <span>可访问资源</span>
                <el-button class="button" type="text" icon="el-icon-delete" @click="deleteRole(role)"></el-button>
              </div>
            </template>
            <div class="permits-tree">
              <el-tree ref="tree" show-checkbox :data="allMenus" :props="defaultProps"
                       :default-checked-keys="selectMenus" node-key="id" :key="index"></el-tree>
              <div class="permits-update-button">
                <el-button size="small" @click="cancelUpdate">取消修改</el-button>
                <el-button type="primary" @click="updateMenuRole(role.id)" size="small">确认修改</el-button>
              </div>
            </div>
          </el-card>
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
</template>

<script>
export default {
  name: "PermitsMana",
  data() {
    return {
      roles: [],
      role: {
        name: "",
        nameZh: ""
      },
      activeNames: "-1",
      allMenus: [],
      defaultProps: {
        children: "children",
        label: "name" //识别标签名
      },
      selectMenus: []
    }
  },
  methods: {
    changeCollapse(rid) {
      if (rid) {
        this.initSelectMenus(rid);
        this.initAllMenus();
      }
    },
    cancelUpdate() {
      this.activeNames = -1;
    },
    /**
    * @Description 发送请求
    * @Return
    * @Author 脱氧核糖
    * @Date 2021/9/5 15:12
    */
    initRoles() {
      this.getRequest("/permit/").then(resp => {
        if (resp) {
          this.roles = resp;
        }
      })
    },
    initAllMenus() {
      this.getRequest("/permit/menus/").then(resp => {
        if (resp) {
          this.allMenus = resp;
        }
      });
    },
    initSelectMenus(rid) {
      this.getRequest("/permit/mid/" + rid, rid).then(resp => {
        if (resp) {
          this.selectMenus = resp;
        }
      });
    },
    updateMenuRole(rid) {
      let tree = this.$refs.tree;
      let selectedKeys = tree.getCheckedKeys(true);
      console.log(selectedKeys);
      let url = "/permit/?rid=" + rid;
      selectedKeys.forEach(key => {
        url += "&mids=" + key;
      });
      this.putRequest(url).then(resp => {
        if (resp) {
          this.initRoles();
          this.activeNames = -1;
        }
      });
    },
    addRole() {
      if (this.role.name && this.role.nameZh) {
        this.postRequest("/permit/", this.role).then(resp => {
          if (resp) {
            this.role.name = "";
            this.role.nameZh = "";
            this.initRoles();
          }
        });
      } else {
        this.$message.error("字段不能为空");
      }
    },
    deleteRole(role) {
      this.$confirm("此操作将永久删除【" + role.nameZh + "】角色, 是否继续？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.deleteRequest("/permit/" + role.id).then(resp => {
          if (resp) {
            this.initRoles();
          }
        })
      }).catch(() => {
        this.$message({
          type: "info",
          message: "已取消操作"
        });
      });
    }
  },

  mounted() {
    this.initRoles();
  }
}
</script>

<style scoped>
.permits-input {
  margin-top: 10px;
  margin-bottom: 30px;
  margin-left: 45px;
  display: flex;
}
.add-role1-input {
  width: 300px;
  margin-right: 10px;
}
.add-role2-input {
  width: 210px;
  margin-right: 10px;
}

.permits-collapse {
  width: 1200px;
}

.button {
  float: right;
  padding: 3px;
}

.permits-update-button {
  display: flex;
  justify-content: flex-end;
}
</style>