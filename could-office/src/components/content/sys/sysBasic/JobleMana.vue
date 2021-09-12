<template>
  <div class="joble-mana">
    <div class="joble-input">
      <el-input class="joble-add-input" v-model="joble.name" placeholder="添加职称等级" suffix-icon="el-icon-plus"></el-input>
      <el-select v-model="joble.titleLevel" placeholder="职称等级" style="width: 110px; margin-right: 10px;">
        <el-option v-for="item in titleLevels" :key="item" :label="item" :value="item"></el-option>
      </el-select>
      <el-button class="add-button" type="primary" icon="el-icon-plus" @click="addJobLevel">添加</el-button>
      <el-button type="danger" :disabled="this.multipleSelection.length === 0" @click="deleteJobLevels">批量删除</el-button>
    </div>
    <div class="joble-table" align="center">
      <el-table :data="jobles" stripe @selection-change="handleSelectionChange" height="450px">
        <el-table-column type="selection" width="110px" align="center"></el-table-column>
        <el-table-column prop="id" label="编号" width="110px" align="center"></el-table-column>
        <el-table-column prop="name" label="职称名称" width="195px" align="center"></el-table-column>
        <el-table-column prop="titleLevel" label="职称等级" width="195px" align="center"></el-table-column>
        <el-table-column prop="createDate" label="创建时间" width="195px" align="center"></el-table-column>
        <el-table-column prop="enabled" label="是否启用" width="175px" align="center">
          <template #default="scope">
            <el-tag v-if="scope.row.enabled" type="success">已启用</el-tag>
            <el-tag v-else type="danger">未启用</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200px" align="center">
          <template #default="scope">
            <el-button size="small" @click="showEditView(scope.row)">修改</el-button>
            <el-button size="small" type="danger" @click="deleteJobLevel(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="dialog">
      <el-dialog title="编辑职称" v-model="dialogVisible" width="30%">
        <div class="joble-tag">
          <el-tag>职称名称</el-tag>
          <el-input class="update-joble-input" v-model="updateJoble.name" size="small"></el-input><br/>
          <el-tag>职称等级</el-tag>
          <el-select v-model="updateJoble.titleLevel" size="small"
                     style="width: 110px; margin-left: 10px; margin-top: 10px; margin-bottom: 10px;">
            <el-option v-for="item in titleLevels" :key="item" :label="item" :value="item"></el-option>
          </el-select><br/>
          <el-tag>是否启用</el-tag>
          <el-switch class="job-level-switch" v-model="updateJoble.enabled" active-text="启用" inactive-text="禁用"
                     active-color="#13ce66" inactive-color="#ff4949" ></el-switch>
        </div>
        <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false" size="small">取消</el-button>
        <el-button type="primary" @click="updateJobLevel" size="small">确定</el-button>
      </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script>
export default {
  name: "JobleMana",
  data() {
    return {
      joble: {
        name: "",
        titleLevel: ""
      },
      updateJoble: {
        name: "",
        titleLevel: ""
      },
      titleLevels: ["正高级", "副高级", "中级", "初级"],
      jobles: [],
      dialogVisible: false,
      multipleSelection: []
    }
  },
  methods: {
    showEditView(data) {
      Object.assign(this.updateJoble, data);
      this.dialogVisible = true;
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    /**
    * @Description 发送请求
    * @Return
    * @Author 脱氧核糖
    * @Date 2021/9/4 20:10
    */
    initJobles() {
      this.getRequest("/joblevel/").then(resp => {
        if (resp) {
          this.jobles = resp;
          this.joble.name = "";
          this.joble.titleLevel = "";
        }
      });
    },
    addJobLevel() {
      if (this.joble.name && this.joble.titleLevel) {
        this.postRequest("/joblevel/", this.joble).then(resp => {
          if (resp) {
            this.initJobles();
          }
        })
      } else {
        this.$message.error("字段不能为空");
      }
    },
    updateJobLevel() {
      this.updateJoble.createDate = "";
      this.putRequest("/joblevel/", this.updateJoble).then(resp => {
        if (resp) {
          this.initJobles();
          this.dialogVisible = false;
        }
      });
    },
    deleteJobLevel(data) {
      this.$confirm("此操作将永久删除【" + data.name + "】职称, 是否继续？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.deleteRequest("/joblevel/" + data.id).then(resp => {
          if (resp) {
            this.initJobles();
          }
        })
      }).catch(() => {
        this.$message({
          type: "info",
          message: "已取消操作"
        });
      });
    },
    deleteJobLevels() {
      this.$confirm("此操作将永久删除【" + this.multipleSelection.length + "】条职称, 是否继续？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        let ids = "?";
        this.multipleSelection.forEach(item => {
          ids += "ids=" + item.id + "&";
        })
        this.deleteRequest("/joblevel/" + ids).then(resp => {
          if (resp) {
            this.initJobles();
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
    this.initJobles();
  }
}
</script>

<style scoped>
.joble-add-input {
  width: 300px;
  margin: 10px 10px 20px 45px;
}
.add-button {
  margin-right: 507px;
}

.update-joble-input {
  width: 200px;
  margin-left: 10px;
}
.job-level-switch {
  margin-left: 10px;
  font-size: 14px;
}
</style>