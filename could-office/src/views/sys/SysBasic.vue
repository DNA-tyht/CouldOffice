<template>
  <div class="sys-basic">
    <el-tabs v-model="activeName">
      <el-tab-pane label="部门管理" name="DepMana">
        <dep-mana></dep-mana>
      </el-tab-pane>
      <el-tab-pane label="职位管理" name="PosMana">
        <pos-mana></pos-mana>
      </el-tab-pane>
      <el-tab-pane label="职称管理" name="JobleMana">
        <joble-mana></joble-mana>
      </el-tab-pane>
      <el-tab-pane label="奖惩规则" name="EcMana">
        <ec-mana></ec-mana>
      </el-tab-pane>
      <el-tab-pane label="权限组" name="PermitsMana">
        <permits-mana></permits-mana>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import DepMana from "@/components/content/sys/sysBasic/DepMana";
import EcMana from "@/components/content/sys/sysBasic/EcMana";
import JobleMana from "@/components/content/sys/sysBasic/JobleMana";
import PermitsMana from "@/components/content/sys/sysBasic/PermitsMana";
import PosMana from "@/components/content/sys/sysBasic/PosMana";

export default {
  name: "SysBasic",
  components: {
    DepMana,
    EcMana,
    JobleMana,
    PermitsMana,
    PosMana,
  },
  data() {
    return {
      activeName: "DepMana"
    }
  },
}
</script>

<style scoped>

</style>