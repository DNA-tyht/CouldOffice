<template>
  <div class="dep-mana">
    <div class="dep-input">
      <el-input placeholder="请输入部门名称" prefix-icon="el-icon-search"
                v-model="filterText" clearable></el-input>
    </div>
    <div class="dep-tree">
      <el-tree ref="tree" class="filter-tree" :data="departments" :props="defaultProps"
          default-expand-all :filter-node-method="filterNode" :expand-on-click-node="false">
        <template #default="{ node, data }">
          <span class="custom-tree-node">
            <span>{{ data.name }}</span>
            <span>
              <el-button class="dep-button" type="primary" size="mini" @click="showAddDep(data)">添加部门</el-button>
              <el-button class="dep-button" type="danger" size="mini" @click="deleteDepartment(data)">删除部门</el-button>
            </span>
          </span>
        </template>
      </el-tree>
    </div>
    <el-dialog title="添加部门" v-model="dialogVisible" width="30%">
      <div class="pos-tag">
        <el-tag class="dep-tag">上级部门</el-tag>
        <span>{{pname}}</span><br/>
        <el-tag class="dep-tag">部门名称</el-tag>
        <el-input v-model="department.name" size="small" placeholder="请输入部门名称"
                  style="display: inline-block; width: 200px;"></el-input><br/>
      </div>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false" size="small">取消</el-button>
        <el-button type="primary" @click="addDepartment" size="small">确定</el-button>
      </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "DepMana",
  data() {
    return {
      filterText: "",
      departments: [],
      defaultProps: {
        children: "children",
        label: "name"
      },
      dialogVisible: false,
      department: {
        name: "",
        parentId: -1
      },
      pname: "",
    }
  },
  methods: {
    filterNode(value, data) {
      if (!value)
        return true; //搜索框无值展示所有树形控件
      return data.name.indexOf(value) !== -1;
    },
    showAddDep(data) {
      this.department.parentId = data.id;
      this.pname = data.name;
      this.dialogVisible = true;
    },
    initDepartment() {
      this.department = {
        name: "",
        parentId: -1
      };
      this.pname = "";
    },
    /**
    * @Description 发送请求
    * @Return
    * @Author 脱氧核糖
    * @Date 2021/9/8 15:09
    */
    initDepartments() {
      this.getRequest("/department/").then(resp => {
        if (resp) {
          this.departments = resp;
        }
      });
    },
    addDepartment() {
      this.postRequest("/department/", this.department).then(resp => {
        if (resp) {
          console.log(resp);
          this.initDepartments();
          this.initDepartment();
          this.dialogVisible = false;
        }
      });
    },
    deleteDepartment(data) {
      this.$confirm("此操作将永久删除【" + data.name + "】部门, 是否继续？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.deleteRequest("/department/" + data.id).then(resp => {
          if (resp) {
            this.initDepartments();
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
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val); //监控搜索框
    }
  },
  mounted() {
    this.initDepartments();
  }
}
</script>

<style scoped>
.dep-input {
  width: 500px;
  margin-top: 10px;
  margin-bottom: 20px;
  margin-left: 45px;
}
.dep-tree {
  margin-left: 45px;
  width: 500px;
}

.custom-tree-node {
  display: flex;
  justify-content: space-between;
  width: 100%;
}
.dep-button {
  padding: 2px;
  min-height: 20px;
}

.dep-tag {
  margin-right: 10px;
  margin-bottom: 10px;
}
</style>