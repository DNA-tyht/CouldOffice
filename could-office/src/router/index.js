import { createRouter, createWebHashHistory } from 'vue-router'
import Login from "@/views/Login";
import Home from "@/views/Home";
import EmpBasic from "@/views/emp/EmpBasic";
import EmpAdv from "@/views/emp/EmpAdv";
import PerEmp from "@/views/per/PerEmp";
import PerEc from "@/views/per/PerEc";
import PerTrain from "@/views/per/PerTrain";
import PerSalary from "@/views/per/PerSalary";
import PerMv from "@/views/per/PerMv";
import SalSob from "@/views/sal/SalSob";
import SalSobCfg from "@/views/sal/SalSobCfg";
import SalTable from "@/views/sal/SalTable";
import SalMonth from "@/views/sal/SalMonth";
import SalSearch from "@/views/sal/SalSearch";
import StaAll from "@/views/sta/StaAll";
import StaScore from "@/views/sta/StaScore";
import StaPers from "@/views/sta/StaPers";
import StaRecord from "@/views/sta/StaRecord";
import SysBasic from "@/views/sys/SysBasic";
import SysCfg from "@/views/sys/SysCfg";
import SysLog from "@/views/sys/SysLog";
import SysAdmin from "@/views/sys/SysAdmin";
import SysData from "@/views/sys/SysData";
import SysInit from "@/views/sys/SysInit";

const routes = [
  {
    path: "/",
    redirect: "/login"
  },
  {
    path: "/login",
    name: "Login",
    component: Login
  },
  {
    path: "/home",
    redirect: "/emp/basic",
    name: "Home",
    component: Home,
    children: [
      {
        path: "/emp/basic",
        name: "基本资料",
        component: EmpBasic,
      },
      {
        path: "/emp/adv",
        name: "高级资料",
        component: EmpAdv,
      },
      {
        path: "/per/emp",
        name: "员工资料",
        component: PerEmp,
      },
      {
        path: "/per/ec",
        name: "员工奖惩",
        component: PerEc,
      },
      {
        path: "/per/train",
        name: "员工培训",
        component: PerTrain,
      },
      {
        path: "/per/salary",
        name: "员工调薪",
        component: PerSalary,
      },
      {
        path: "/per/mv",
        name: "员工调动",
        component: PerMv,
      },
      {
        path: "/sal/sob",
        name: "工资账套管理",
        component: SalSob,
      },
      {
        path: "/sal/sobcfg",
        name: "员工账套设置",
        component: SalSobCfg,
      },
      {
        path: "/sal/table",
        name: "工资表管理",
        component: SalTable,
      },
      {
        path: "/sal/month",
        name: "月末处理",
        component: SalMonth,
      },
      {
        path: "/sal/search",
        name: "工资表查询",
        component: SalSearch,
      },
      {
        path: "/sta/all",
        name: "综合信息统计",
        component: StaAll,
      },
      {
        path: "/sta/score",
        name: "员工积分统计",
        component: StaScore,
      },
      {
        path: "/sta/pers",
        name: "人事信息统计",
        component: StaPers,
      },
      {
        path: "/sta/record",
        name: "人事记录统计",
        component: StaRecord,
      },
      {
        path: "/sys/basic",
        name: "基础信息设置",
        component: SysBasic,
      },
      {
        path: "/sys/cfg",
        name: "系统管理",
        component: SysCfg,
      },
      {
        path: "/sys/log",
        name: "操作日志管理",
        component: SysLog,
      },
      {
        path: "/sys/admin",
        name: "操作员管理",
        component: SysAdmin,
      },
      {
        path: "/sys/data",
        name: "备份恢复数据库",
        component: SysData,
      },
      {
        path: "/sys/init",
        name: "初始化数据库",
        component: SysInit,
      },
    ]
  },
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
