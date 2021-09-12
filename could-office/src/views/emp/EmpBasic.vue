<template>
  <div class="emp-basic">
    <div class="emp-input">
      <el-input class="emp-basic-input" placeholder="请输入员工姓名" clearable :disabled="showHighSearch.isShow"
                v-model="empName" @keydown.enter.native="searchEmployee" @clear="initEmployees"></el-input>
      <el-button type="primary" icon="el-icon-search" @click="searchEmployee" :disabled="showHighSearch.isShow">搜索</el-button>
      <el-button type="primary" :icon="showHighSearch.icon" @click="showHighSearchView">高级搜索</el-button>
      <div class="emp-basic-button">
        <el-upload class="upload-button" action="/employee/import" :headers="headers" :show-file-list="false"
                   :before-upload="beforeUpload" :on-success="onSuccess" :on-error="onError"
                   :disabled="importData.disabled">
          <el-button type="success" :icon="importData.btnIcon"
                     :disabled="importData.disabled">{{importData.btnText}}</el-button>
        </el-upload>
        <el-button type="success" @click="exportData" icon="el-icon-download">导出数据</el-button>
        <el-button type="primary" icon="el-icon-plus" @click="showAddEmpView">添加员工</el-button>
      </div>
    </div>
    <transition name="slide-fade">
      <div class="high-search" v-show="showHighSearch.isShow">
        <el-row>
          <el-col :span="5">
            <span class="search-item">政治面貌</span>
            <el-select class="search-input" v-model="searchData.politicId" placeholder="政治面貌" size="small">
              <el-option v-for="item in politicsstatus" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
          </el-col>
          <el-col :span="4">
            <span class="search-item">民族</span>
            <el-select class="search-input" v-model="searchData.nationId" placeholder="民族" size="small">
              <el-option v-for="item in nations" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
          </el-col>
          <el-col :span="4">
            <span class="search-item">职位</span>
            <el-select class="search-input" v-model="searchData.posId" placeholder="职位" size="small">
              <el-option v-for="item in positions" :key="item.id"
                         :label="item.name" :value="item.id"></el-option>
            </el-select>
          </el-col>
          <el-col :span="6">
            <span class="search-item" style="margin-top: 6px;">聘用形式</span>
            <el-radio-group class="search-input" v-model="searchData.engageForm">
              <el-radio label="劳动合同">劳动合同</el-radio>
              <el-radio label="劳务合同">劳务合同</el-radio>
            </el-radio-group>
          </el-col>
          <el-col :span="3">
            <el-button size="small" @click="cancelHighSearch" style="width: 72px;">取消</el-button>
          </el-col>
        </el-row>
        <el-row style="margin-top: 10px;">
          <el-col :span="5">
            <span class="search-item">所属部门</span>
            <el-popover placement="right" title="请选择部门" :width="250"
                        trigger="hover" v-model:visible2="visible2" @click="showDepView2">
              <el-tree :data="departments" :props="defaultProps" default-expand-all
                       @node-click="searchHandleNodeClick"></el-tree>
              <template #reference>
                <el-button class="virtual-input" @click="visible2 = !visible2" size="small"
                           icon="el-icon-edit" style="margin-left: 10px; width: 125px;">{{showDepName}}</el-button>
              </template>
            </el-popover>
          </el-col>
          <el-col :span="4">
            <span class="search-item">学历</span>
            <el-select class="search-input" v-model="searchData.tiptopDegree" placeholder="学历" size="small">
              <el-option v-for="item in tiptopDegrees" :key="item"
                         :label="item" :value="item"></el-option>
            </el-select>
          </el-col>
          <el-col :span="4">
            <span class="search-item">职称</span>
            <el-select class="search-input" v-model="searchData.jobLevelId" placeholder="职称" size="small">
              <el-option v-for="item in jobLevels" :key="item.id"
                         :label="item.name" :value="item.id"></el-option>
            </el-select>
          </el-col>
          <el-col :span="6">
            <span class="search-item">合同日期</span>
            <el-date-picker v-model="searchData.beginDateScope" type="daterange"
                            range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"
                            value-format="YYYY-MM-DD" placeholder="合同日期" size="small"
                            style="margin-left: 10px; width: 200px;"></el-date-picker>
          </el-col>
          <el-col :span="3">
            <el-button icon="el-icon-search" type="primary" size="small" @click="highSearch">搜索</el-button>
          </el-col>
        </el-row>
      </div>
    </transition>
    <div class="emp-table" align="center">
      <el-table :data="employees" v-loading="loading" stripe  height="550px">
        <el-table-column type="selection" width="30px" align="center"></el-table-column>
        <el-table-column prop="id" label="ID" width="80px" align="center"></el-table-column>
        <el-table-column fixed="left" prop="name" label="姓名" width="80px" align="center"></el-table-column>
        <el-table-column prop="workID" label="工号" width="100px" align="center"></el-table-column>
        <el-table-column prop="gender" label="性别" width="50px" align="center"></el-table-column>
        <el-table-column prop="birthday" label="出生日期" width="100px" align="center"></el-table-column>
        <el-table-column prop="idCard" label="身份证号" width="200px" align="center"></el-table-column>
        <el-table-column prop="wedlock" label="婚姻状况" width="100px" align="center"></el-table-column>
        <el-table-column prop="nation.name" label="民族" width="80px" align="center"></el-table-column>
        <el-table-column prop="nativePlace" label="籍贯" width="100px" align="center"></el-table-column>
        <el-table-column prop="politicsStatus.name" label="政治面貌" width="200px" align="center"></el-table-column>
        <el-table-column prop="phone" label="电话号码" width="120px" align="center"></el-table-column>
        <el-table-column prop="address" label="联系地址" width="300px" align="center"></el-table-column>
        <el-table-column prop="department.name" label="所属部门" width="100px" align="center"></el-table-column>
        <el-table-column prop="jobLevel.name" label="职称" width="150px" align="center"></el-table-column>
        <el-table-column prop="position.name" label="职位" width="100px" align="center"></el-table-column>
        <el-table-column prop="engageForm" label="聘用形式" width="100px" align="center"></el-table-column>
        <el-table-column prop="tiptopDegree" label="最高学历" width="100px" align="center"></el-table-column>
        <el-table-column prop="school" label="毕业院校" width="200px" align="center"></el-table-column>
        <el-table-column prop="specialty" label="专业" width="200px" align="center"></el-table-column>
        <el-table-column prop="workState" label="在职状态" width="110px" align="center"></el-table-column>
        <el-table-column prop="beginDate" label="入职日期" width="100px" align="center"></el-table-column>
        <el-table-column prop="conversionTime" label="转正日期" width="100px" align="center"></el-table-column>
        <el-table-column prop="beginContract" label="合同起始日期" width="150px" align="center"></el-table-column>
        <el-table-column prop="endContract" label="合同截止日期" width="150px" align="center"></el-table-column>
        <el-table-column label="合同期限" width="100px" align="center">
          <template #default="scope">
            <el-tag class="table-scope" size="small">{{scope.row.contractTerm}}</el-tag> 年
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="200px" align="center">
          <template #default="scope">
            <el-button class="table-button" @click="showUpdateEmpView(scope.row)" size="small">编辑</el-button>
            <el-button class="table-button" @click="deleteEmployee(scope.row)"
                       size="small" type="danger">删除</el-button>
            <el-button class="table-button" type="primary" @click="showEmployee(scope.row)" size="small">查看高级资料</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination layout="total, sizes, prev, pager, next, jumper" :total="total" style="margin-top: 10px;"
                     @current-change="currentChange" @size-change="sizeChange" :page-size="size"></el-pagination>
    </div>
    <div class="dialog">
      <el-dialog :title="title" v-model="dialogVisible" width="80%">
        <div class="emp-basic-tag">
          <el-form ref="empForm" :model="employee" :rules="rules">
            <el-row>
              <el-col :span="6">
                <el-form-item prop="name" label="姓名">
                  <el-input class="edit-input" v-model="employee.name" placeholder="请输入员工姓名"
                            prefix-icon="el-icon-edit" size="small" :disabled="!isUpadte"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item prop="gender" label="性别">
                  <el-radio-group v-model="employee.gender" :disabled="!isUpadte">
                    <el-radio label="男">男</el-radio>
                    <el-radio label="女">女</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item prop="birthday" label="出生日期">
                  <el-date-picker v-model="employee.birthday" type="date" size="small" style="width: 180px;"
                                  value-format="YYYY-MM-DD" placeholder="出生日期" :disabled="!isUpadte"></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item prop="politicId" label="政治面貌">
                  <el-select class="edit-input" v-model="employee.politicId" :disabled="!isUpadte" placeholder="政治面貌" size="small">
                    <el-option v-for="item in politicsstatus" :key="item.id" :label="item.name" :value="item.id"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="6">
                <el-form-item prop="nationId" label="民族">
                  <el-select class="edit-input" v-model="employee.nationId" :disabled="!isUpadte" placeholder="民族" size="small">
                    <el-option v-for="item in nations" :key="item.id" :label="item.name" :value="item.id"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item prop="nativePlace" label="籍贯">
                  <el-input class="edit-input" v-model="employee.nativePlace" placeholder="请输入籍贯"
                            prefix-icon="el-icon-edit" size="small" :disabled="!isUpadte"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item prop="email" label="电子邮箱">
                  <el-input class="edit-input" v-model="employee.email" placeholder="请输入电子邮箱"
                            prefix-icon="el-icon-message" size="small" :disabled="!isUpadte"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item prop="address" label="联系地址">
                  <el-input class="edit-input" v-model="employee.address" placeholder="请输入联系地址"
                            prefix-icon="el-icon-edit" size="small" :disabled="!isUpadte"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="6">
                <el-form-item prop="posId" label="职位">
                  <el-select class="edit-input" v-model="employee.posId" :disabled="!isUpadte" placeholder="职位" size="small">
                    <el-option v-for="item in positions" :key="item.id"
                               :label="item.name" :value="item.id"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item prop="jobLevelId" label="职称">
                  <el-select class="edit-input" v-model="employee.jobLevelId" :disabled="!isUpadte" placeholder="职称" size="small">
                    <el-option v-for="item in jobLevels" :key="item.id"
                               :label="item.name" :value="item.id"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item prop="departmentId" label="所属部门">
                  <el-popover placement="right" title="请选择部门" :width="250" :disabled="!isUpadte"
                      trigger="hover" v-model:visible1="visible1" @click="showDepView1">
                    <el-tree :data="departments" :props="defaultProps" default-expand-all @node-click="handleNodeClick"></el-tree>
                    <template #reference>
                      <el-button class="virtual-input" @click="visible1 = !visible1"
                                 size="small" icon="el-icon-edit">{{showDepName}}</el-button>
                    </template>
                  </el-popover>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item prop="phone" label="电话号码">
                  <el-input class="edit-input" v-model="employee.phone" placeholder="请输入电话号码"
                            prefix-icon="el-icon-phone" size="small" :disabled="!isUpadte"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="6">
                <el-form-item prop="workID" label="工号">
                  <el-input class="edit-input" v-model="employee.workID" placeholder="请输入工号"
                            prefix-icon="el-icon-edit" size="small" disabled></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item prop="tiptopDegree" label="学历">
                  <el-select class="edit-input" v-model="employee.tiptopDegree" :disabled="!isUpadte" placeholder="学历" size="small">
                    <el-option v-for="item in tiptopDegrees" :key="item"
                               :label="item" :value="item"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item prop="school" label="毕业院校">
                  <el-input class="edit-input" v-model="employee.school" placeholder="请输入毕业院校"
                            prefix-icon="el-icon-edit" size="small" :disabled="!isUpadte"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item prop="specialty" label="专业名称">
                  <el-input class="edit-input" v-model="employee.specialty" placeholder="请输入专业名称"
                            prefix-icon="el-icon-edit" size="small" :disabled="!isUpadte"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="6">
                <el-form-item prop="beginDate" label="入职日期">
                  <el-date-picker v-model="employee.beginDate" type="date" size="small" :disabled="!isUpadte"
                                  value-format="YYYY-MM-DD" placeholder="入职日期" style="width: 152px;"></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item prop="conversionTime" label="转正日期">
                  <el-date-picker v-model="employee.conversionTime" type="date" size="small" :disabled="!isUpadte"
                                  value-format="YYYY-MM-DD" placeholder="转正日期" style="width: 152px;"></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item prop="beginContract" label="合同起始日期">
                  <el-date-picker v-model="employee.beginContract" type="date" size="small" :disabled="!isUpadte"
                                  value-format="YYYY-MM-DD" placeholder="合同起始日期" style="width: 152px;"></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item prop="endContract" label="合同截止日期">
                  <el-date-picker v-model="employee.endContract" type="date" size="small" :disabled="!isUpadte"
                                  value-format="YYYY-MM-DD" placeholder="合同截止日期" style="width: 152px;"></el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item prop="engageForm" label="聘用形式">
                  <el-radio-group v-model="employee.engageForm" :disabled="!isUpadte">
                    <el-radio label="劳动合同">劳动合同</el-radio>
                    <el-radio label="劳务合同">劳务合同</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item prop="wedlock" label="婚姻状况">
                  <el-radio-group v-model="employee.wedlock" :disabled="!isUpadte">
                    <el-radio label="已婚">已婚</el-radio>
                    <el-radio label="未婚">未婚</el-radio>
                    <el-radio label="离异">离异</el-radio>
                    <el-radio label="丧偶">丧偶</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item prop="idCard" label="身份证号">
                  <el-input class="edit-input" v-model="employee.idCard" placeholder="请输入身份证号"
                            prefix-icon="el-icon-edit" size="small" :disabled="!isUpadte"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>
        <template #footer>
      <span v-if="isUpadte" class="dialog-footer">
        <el-button @click="dialogVisible = false" size="small">取消</el-button>
        <el-button type="primary" @click="addOrUpdateEmployee" size="small">确定</el-button>
      </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script>
export default {
  name: "EmpBasic",
  data() {
    return {
      employees: [],
      loading: false,
      total: 0,
      currentPage: 1,
      size: 10,
      empName: "",
      dialogVisible: false,
      employee: {
        id: null,
        name: "",
        gender: "",
        birthday: "",
        idCard: "",
        wedlock: "",
        nationId: null,
        nativePlace: "",
        politicId: null,
        email: "",
        phone: "",
        address: "",
        departmentId: null,
        jobLevelId: null,
        posId: null,
        engageForm: "",
        tiptopDegree: "",
        specialty: "",
        school: "",
        beginDate: "",
        workState: "在职",
        workID: "",
        contractTerm: null,
        conversionTime: "",
        notWorkDate: null,
        beginContract: "",
        endContract: "",
        workAge: null,
        salaryId: null
      },
      nations: [],
      jobLevels: [],
      politicsstatus: [],
      positions: [],
      departments: [],
      tiptopDegrees: ["博士", "硕士", "本科", "大专", "高中", "初中", "小学", "其他"],
      visibl1: false,
      visibl2: false,
      defaultProps: {
        children: "children",
        label: "name"
      },
      showDepName: "所属部门",
      rules: { //表单提交校验
        name: [{required: true, message: "请输入姓名", trigger: "blur"}],
        gender: [{required: true, message: "请输入性别", trigger: "blur"}],
        birthday: [{required: true, message: "请输入出生日期", trigger: "blur"}],
        idCard: [{required: true, message: "请输入身份证号", trigger: "blur"},
                 {partten: /^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/,
                  message: "身份证号码不正确", trigger: "blur"}],
        wedlock: [{required: true, message: "请输入婚姻状况", trigger: "blur"}],
        nationId: [{required: true, message: "请输入民族", trigger: "blur"}],
        nativePlace: [{required: true, message: "请输入籍贯", trigger: "blur"}],
        politicId: [{required: true, message: "请输入政治面貌", trigger: "blur"}],
        email: [{required: true, message: "请输入邮箱地址", trigger: "blur"},
                {type: "email", message: "邮箱地址格式不正确", trigger: "blur"}],
        phone: [{required: true, message: "请输入电话号码", trigger: "blur"}],
        address: [{required: true, message: "请输入联络地址", trigger: "blur"}],
        departmentId: [{required: true, message: "请输入所属部门", trigger: "blur"}],
        jobLevelId: [{required: true, message: "请输入职称", trigger: "blur"}],
        posId: [{required: true, message: "请输入职位", trigger: "blur"}],
        engageForm: [{required: true, message: "请输入聘用方式", trigger: "blur"}],
        tiptopDegree: [{required: true, message: "请输入最高学历", trigger: "blur"}],
        specialty: [{required: true, message: "请输入专业", trigger: "blur"}],
        school: [{required: true, message: "请输入毕业院校", trigger: "blur"}],
        beginDate: [{required: true, message: "请输入入职日期", trigger: "blur"}],
        workState: [{required: true, message: "请输入工作状态", trigger: "blur"}],
        workID: [{required: true, message: "请输入工号", trigger: "blur"}],
        contractTerm: [{required: true, message: "请输入合同期限", trigger: "blur"}],
        conversionTime: [{required: true, message: "请输入转正日期", trigger: "blur"}],
        notWorkDate: [{required: true, message: "请输入离职日期", trigger: "blur"}],
        beginContract: [{required: true, message: "请输入合同起始日期", trigger: "blur"}],
        endContract: [{required: true, message: "请输入合同截止日期", trigger: "blur"}],
        workAge: [{required: true, message: "请输入工龄", trigger: "blur"}]
      },
      title: "",
      isUpadte: false,
      importData: {
        btnText: "导入数据",
        btnIcon: "el-icon-upload2",
        disabled: false
      },
      headers: {
        Authorization: window.sessionStorage.getItem("tokenStr")
      },
      showHighSearch: {
        isShow: false,
        icon: "el-icon-arrow-down"
      },
      searchData: {
        politicId: null,
        nationId: null,
        departmentId: null,
        jobLevelId: null,
        posId: null,
        tiptopDegree: "",
        engageForm: "",
        beginDateScope: null,
      }
    }
  },
  methods: {
    currentChange(currentPage) {
      this.currentPage = currentPage;
      this.initEmployees();
    },
    sizeChange(size) {
      this.size = size;
      this.initEmployees();
    },
    searchEmployee() {
      this.initEmployees();
    },
    showAddEmpView() {
      this.employee = {
        id: null,
        name: "",
        gender: "",
        birthday: "",
        idCard: "",
        wedlock: "",
        nationId: null,
        nativePlace: "",
        politicId: null,
        email: "",
        phone: "",
        address: "",
        departmentId: null,
        jobLevelId: null,
        posId: null,
        engageForm: "",
        tiptopDegree: "",
        specialty: "",
        school: "",
        beginDate: "",
        workState: "在职",
        workID: "",
        contractTerm: null,
        conversionTime: "",
        notWorkDate: null,
        beginContract: "",
        endContract: "",
        workAge: null,
        salaryId: null
      };
      this.showDepName = "所属部门";
      this.title = "添加员工"
      this.isUpadte = true;
      this.dialogVisible = true;
      this.initData();
      this.initPositions();
      this.getMaxWorkId();
    },
    showDepView1() {
      this.visible1 = !this.visible1;
    },
    showDepView2() {
      this.visible2 = !this.visible2;
    },
    handleNodeClick(data) {
      this.visible1 = !this.visible1;
      this.employee.departmentId = data.id;
      this.showDepName = data.name;
    },
    searchHandleNodeClick(data) {
      this.visibl2 = !this.visibl2;
      this.searchData.departmentId = data.id;
      this.showDepName = data.name;
    },
    showUpdateEmpView(data) {
      this.title = "编辑员工信息";
      this.isUpadte = true;
      this.dialogVisible = true;
      this.employee = data;
      this.showDepName = data.department.name;
      this.initPositions();
      this.initData();
    },
    showEmployee(data) {
      this.title = "员工高级信息";
      this.isUpadte = false;
      this.dialogVisible = true;
      this.employee = data;
      this.showDepName = data.department.name;
      this.initPositions();
      this.initData();
    },
    beforeUpload() {
      this.importData.btnText = "正在导入";
      this.importData.btnIcon = "el-icon-loading";
      this.importData.disabled = true;
    },
    onSuccess() {
      this.importData.btnText = "导入数据";
      this.importData.btnIcon = "el-icon-upload2";
      this.importData.disabled = false;
      this.$message.success("上传成功");
      this.initEmployees();
    },
    onError() {
      this.importData.btnText = "导入数据";
      this.importData.btnIcon = "el-icon-upload2";
      this.$message.error("上传失败");
      this.importData.disabled = false;
    },
    showHighSearchView() {
      this.showHighSearch.isShow = !this.showHighSearch.isShow;
      if (this.showHighSearch.isShow) {
        this.showHighSearch.icon = "el-icon-arrow-up";
      } else {
        this.showHighSearch.icon = "el-icon-arrow-down";
      }
    },
    highSearch() {
      this.initEmployees("advanced");
    },
    cancelHighSearch() {
      this.searchData = {
        politicId: null,
        nationId: null,
        departmentId: null,
        jobLevelId: null,
        posId: null,
        tiptopDegree: "",
        engageForm: "",
        beginDateScope: null,
      };
      this.showDepName = "所属部门";
    },
    /**
    * @Description 发送请求
    * @Return
    * @Author 脱氧核糖
    * @Date 2021/9/11 11:46
    */
    initEmployees(type) {
      this.loading = true;
      let url = "/employee/?currentPage=" + this.currentPage + "&size=" + this.size;
      if (type && type == "advanced") {
        if (this.searchData.politicId) {
          url += "&politicId=" + this.searchData.politicId
        }
        if (this.searchData.nationId) {
          url += "&nationId=" + this.searchData.nationId;
        }
        if (this.searchData.posId) {
          url += "&posId=" + this.searchData.posId;
        }
        if (this.searchData.jobLevelId) {
          url += "&jobLevelId=" + this.searchData.jobLevelId;
        }
        if (this.searchData.engageForm) {
          url += "&engageForm=" + this.searchData.engageForm;
        }
        if (this.searchData.departmentId) {
          url += "&departmentId=" + this.searchData.departmentId;
        }
        if (this.searchData.beginDateScope) {
          url += "&beginDateScope=" + this.searchData.beginDateScope;
        }
      } else {
        url += "&name=" + this.empName;
      }
      this.getRequest(url).then(resp => {
        if (resp) {
          this.employees = resp.data;
          this.total = resp.total;
        }
        this.loading = false;
      });
    },
    initData() {
      if (!window.sessionStorage.getItem("nations")) {
        this.getRequest("/nation/").then(resp => {
          if (resp) {
            this.nations = resp;
            window.sessionStorage.setItem("nations", JSON.stringify(resp));
          }
        });
      } else {
        this.nations = JSON.parse(window.sessionStorage.getItem("nations"))
      }
      if (!window.sessionStorage.getItem("jobLevels")) {
        this.getRequest("/joblevel/").then(resp => {
          if (resp) {
            this.jobLevels = resp;
            window.sessionStorage.setItem("jobLevels", JSON.stringify(resp));
          }
        });
      } else {
        this.jobLevels = JSON.parse(window.sessionStorage.getItem("jobLevels"))
      }
      if (!window.sessionStorage.getItem("politicsstatus")) {
        this.getRequest("/politics-status/").then(resp => {
          if (resp) {
            this.politicsstatus = resp;
            window.sessionStorage.setItem("politicsstatus", JSON.stringify(resp));
          }
        });
      } else {
        this.politicsstatus = JSON.parse(window.sessionStorage.getItem("politicsstatus"))
      }
      if (!window.sessionStorage.getItem("departments")) {
        this.getRequest("/department/").then(resp => {
          if (resp) {
            this.departments = resp;
            window.sessionStorage.setItem("departments", JSON.stringify(resp));
          }
        });
      } else {
        this.departments = JSON.parse(window.sessionStorage.getItem("departments"))
      }
    },
    initPositions() {
      this.getRequest("/position/").then(resp => {
        if (resp) {
          this.positions = resp;
        }
      });
    },
    getMaxWorkId() {
      this.getRequest("/employee/maxWorkID").then(resp => {
        if (resp) {
          this.employee.workID = resp.obj;
        }
      });
    },
    addOrUpdateEmployee() {
      if (this.employee.id) { //更新
        this.$refs.empForm.validate((valid) => {
          if (valid) {
            this.putRequest("/employee/", this.employee).then(resp => {
              if (resp) {
                this.dialogVisible = false;
                this.initEmployees();
              }
            });
          }
        });
      } else { //添加
        this.$refs.empForm.validate((valid) => {
          if (valid) {
            this.postRequest("/employee/", this.employee).then(resp => {
              if (resp) {
                this.dialogVisible = false;
                this.initEmployees();
              }
            });
          }
        });
      }
    },
    deleteEmployee(data) {
      this.$confirm("此操作将永久删除【" + data.name + "】员工, 是否继续？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.deleteRequest("/employee/" + data.id).then(resp => {
          if (resp) {
            this.initEmployees();
          }
        })
      }).catch(() => {
        this.$message({
          type: "info",
          message: "已取消操作"
        });
      });
    },
    exportData() {
      this.downloadRequest("/employee/export");
    }
  },
  mounted() {
    this.initEmployees();
    this.initData();
    this.initPositions();
  }
}
</script>

<style scoped>
.emp-input {
  margin-left: 45px;
  margin-top: 10px;
  display: flex;
  margin-bottom: 20px;
}
.emp-basic-input {
  width: 300px;
  margin-right: 10px;
}
.emp-basic-button {
  margin-left: 250px;
}

.emp-table {
  margin-left: 45px;
  width: 1200px;
}
.table-button {
  padding: 3px;
  min-height: 10px;
}
.table-scope {
  margin-right: 5px;
}

.edit-input {
  width: 180px;
}
.virtual-input {
  width: 180px;
  height: 32px;
  font-size: 13px;
  color: #b7b8bd;;
}

.upload-button {
  display: inline-flex;
  margin-right: 10px;
}

.high-search {
  border: 1px solid var(--color-one);
  border-radius: 5px;
  box-sizing: border-box;
  padding: 10px;
  margin: 10px 0px;
  margin-left: 45px;
  width: 1200px;
}
.search-item {
  font-size: 14px;
  color: #445671;
  display: inline-flex;
}
.search-input {
  display: inline-flex;
  margin-left: 10px;
  width: 125px;
}
/* 可以设置不同的进入和离开动画 */
/* 设置持续时间和动画函数 */
.slide-fade-enter-active {
  transition: all .8s ease;
}
.slide-fade-leave-active {
  transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
}
.slide-fade-enter, .slide-fade-leave-to
  /* .slide-fade-leave-active for below version 2.1.8 */ {
  transform: translateX(10px);
  opacity: 0;
}
</style>