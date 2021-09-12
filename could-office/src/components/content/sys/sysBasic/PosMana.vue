<template>
  <div class="pos-mana">
    <div class="pos-input">
      <el-input class="add-pos-input" placeholder="添加职位" suffix-icon="el-icon-plus"
                v-model="pos.name" @keydown.enter.native="addPosition"></el-input>
      <el-button class="add-button" icon="el-icon-plus" type="primary" @click="addPosition">添加</el-button>
      <el-button type="danger" :disabled="this.multipleSelection.length === 0" @click="deletePositions">批量删除</el-button>
    </div>
    <div class="pos-table" align="center">
      <el-table :data="positions" stripe @selection-change="handleSelectionChange" height="450px">
        <el-table-column type="selection" width="110px" align="center"></el-table-column>
        <el-table-column prop="id" label="编号" width="110px" align="center"></el-table-column>
        <el-table-column prop="name" label="职位" width="260px" align="center"></el-table-column>
        <el-table-column prop="createDate" label="创建时间" width="260px" align="center"></el-table-column>
        <el-table-column prop="enabled" label="是否启用" width="260px" align="center">
          <template #default="scope">
            <el-tag v-if="scope.row.enabled" type="success">已启用</el-tag>
            <el-tag v-else type="danger">未启用</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200px" align="center">
          <template #default="scope">
            <el-button size="small" @click="showEditView(scope.$index, scope.row)">修改</el-button>
            <el-button size="small" type="danger" @click="deletePosition(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog title="编辑职位" v-model="dialogVisible" width="30%">
      <div class="pos-tag">
        <el-tag>职位名称</el-tag>
        <el-input class="update-pos-input" v-model="updatePos.name" size="small"></el-input><br/>
        <el-tag style="margin-top: 10px;">是否启用</el-tag>
        <el-switch class="pos-switch" v-model="updatePos.enabled" active-text="启用" inactive-text="禁用"
                   active-color="#13ce66" inactive-color="#ff4949" ></el-switch>
      </div>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false" size="small">取消</el-button>
        <el-button type="primary" @click="updatePosition" size="small">确定</el-button>
      </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "PosMana",
  data() {
    return {
      pos: {
        name: ""
      },
      updatePos: {
        name: ""
      },
      positions: [],
      dialogVisible: false,
      multipleSelection: []
    }
  },
  methods: {
    showEditView(index, data) {
      Object.assign(this.updatePos.name);
      this.updatePos = data;
      this.dialogVisible = true;
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    /**
    * @Description 发送请求
    * @Return
    * @Author 脱氧核糖
    * @Date 2021/9/4 14:27
    */
    initPositions() {
      this.getRequest("/position/").then(resp => {
        this.positions = resp;
      })
    },
    addPosition() {
      if (this.pos.name) {
        this.postRequest("/position/", this.pos).then(resp => {
          if (resp) {
            this.initPositions();
            this.pos.name = "";
          }
        });
      } else {
        this.$message.error("字段不能为空");
      }
    },
    updatePosition() {
      this.updatePos.createDate = "";
      this.putRequest("/position/", this.updatePos).then(resp => {
        if (resp) {
          this.initPositions();
          this.dialogVisible = false;
        }
      })
    },
    deletePosition(index, data) {
      this.$confirm("此操作将永久删除【" + data.name + "】职位, 是否继续？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.deleteRequest("/position/" + data.id).then(resp => {
          if (resp) {
            this.initPositions();
          }
        })
      }).catch(() => {
        this.$message({
          type: "info",
          message: "已取消操作"
        });
      });
    },
    deletePositions() {
      this.$confirm("此操作将永久删除【" + this.multipleSelection.length + "】条职位, 是否继续？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        let ids = "?";
        this.multipleSelection.forEach(item => {
          ids += "ids=" + item.id + "&";
        })
        this.deleteRequest("/position/" + ids).then(resp => {
          if (resp) {
            this.initPositions();
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
    this.initPositions();
  }
}
</script>

<style scoped>
.add-pos-input {
  width: 300px;
  margin: 10px 10px 20px 45px;
}
.add-button {
  margin-right: 643px;
}

.update-pos-input {
  width: 200px;
  margin-left: 10px;
}
.pos-switch {
  margin-top: 10px;
  margin-left: 10px;
}
</style>