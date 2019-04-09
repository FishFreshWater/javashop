<template>
  <el-dialog
    class="my-dialog"
    :fullscreen="true"
    :modal="false"
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="用户昵称" prop="nickname">
       <el-input v-model="dataForm.nickname" placeholder="用户昵称"></el-input>
      </el-form-item>
      <!-- <el-form-item label="手机号" prop="mobile">
       <el-input v-model="dataForm.mobile" placeholder="手机号"></el-input>
      </el-form-item> -->
      <el-form-item label="用户生日" prop="birthday">
        <div class="block">
          <el-date-picker
            v-model="dataForm.birthday"
            align="right"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择日期"
            >
          </el-date-picker>
        </div>
      </el-form-item>
      <el-form-item label="用户邮箱" prop="email">
        <el-input v-model="dataForm.email" placeholder="用户邮箱"></el-input>
      </el-form-item>
  
      <el-form-item label="性别" size="mini">
        <el-radio-group v-model="dataForm.gender">
          <el-radio :label=0>男</el-radio>
          <el-radio :label=1>女</el-radio>
        </el-radio-group>
      </el-form-item>
          <!-- <el-form-item label="微信id" prop="weixinOpenid">
          <el-input v-model="dataForm.weixinOpenid" placeholder="微信id"></el-input>
      </el-form-item> -->
<!--       

      <el-form-item label="用户姓名" prop="username">
        <el-input v-model="dataForm.username" placeholder="用户姓名"></el-input>
      </el-form-item>

      <el-form-item label="用户类型" prop="userType">
        <el-input v-model="dataForm.userType" placeholder="用户类型(1:买家;2:卖家)"></el-input>
      </el-form-item>

      <el-form-item label="用户密码" prop="password">
       <el-input v-model="dataForm.password" placeholder="用户密码"></el-input>
      </el-form-item>

      <el-form-item label="积分" prop="integral">
       <el-input v-model="dataForm.integral" placeholder="积分"></el-input>
      </el-form-item>
      <el-form-item label="用户余额" prop="balanceAmt">
       <el-input v-model="dataForm.balanceAmt" placeholder="用户余额"></el-input>
      </el-form-item>
      <el-form-item label="上次登录ip" prop="lastLoginIp">
       <el-input v-model="dataForm.lastLoginIp" placeholder="上次登录ip" :disabled="true"></el-input>
      </el-form-item>

      <el-form-item label="上次登录时间" prop="lastLoginTime">
        <el-date-picker
            v-model="dataForm.lastLoginTime"
            align="right"
            value-format="yyyy-MM-dd HH:mm:ss"
            type="date"
            placeholder="选择日期"
            >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="所属用户组" prop="userLevelId">
       <el-input v-model="dataForm.userLevelId" placeholder="所属用户组(01:默认;02：待定)"></el-input>
      </el-form-item> -->


    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          username: '',                      
          userType: '',                      
          mobile: '',                      
          password: '',                      
          email: '',                      
          integral: '',                      
          balanceAmt: '',                      
          lastLoginIp: '',                      
          birthday: '',                      
          lastLoginTime: '',                     
          userLevelId: '',                      
          nickname: '',                      
          weixinOpenid: '',                      
          gender: '',            
          avatarUrl: '',                      
          city: '',                      
          openid: '',                      
          province: ''            
        },
        dataRule: {
        //   username: [
        //     {required: true, message: '用户姓名不能为空', trigger: 'blur' }
        //   ]
        // ,
        //   userType: [
        //     {required: true, message: '用户类型(01:买家;02:卖家)不能为空', trigger: 'blur' }
        //   ]
        // ,
            //   password: [
            //     {required: true, message: '用户密码不能为空', trigger: 'blur' }
            //   ]
            // ,
          mobile: [
            {required: true, message: '手机号不能为空', trigger: 'blur' }
          ]
        ,
          email: [
            {required: true, message: '用户邮箱不能为空', trigger: 'blur' }
          ]
        ,
        //   integral: [
        //     {required: true, message: '积分不能为空', trigger: 'blur' }
        //   ]
        // ,
        //   balanceAmt: [
        //     {required: true, message: '用户余额不能为空', trigger: 'blur' }
        //   ]
        // ,
        //   lastLoginIp: [
        //     {required: true, message: '上次登录ip不能为空', trigger: 'blur' }
        //   ]
        // ,
          birthday: [
            {required: true, message: '用户生日不能为空', trigger: 'blur' }
          ]
        ,
        //   lastLoginTime: [
        //     {required: true, message: '上次登录时间不能为空', trigger: 'blur' }
        //   ]
        // ,
        //   userLevelId: [
        //     {required: true, message: '所属用户组(01:默认;02：待定)不能为空', trigger: 'blur' }
        //   ]
        // ,
          nickname: [
            {required: true, message: '用户昵称不能为空', trigger: 'blur' }
          ]
        ,
        //   weixinOpenid: [
        //     {required: true, message: '微信id不能为空', trigger: 'blur' }
        //   ]
        // ,
          gender: [
            {required: true, message: '性别不能为空', trigger: 'blur' }
          ]
        ,
        //       avatarUrl: [
        //     {required: true, message: '不能为空', trigger: 'blur' }
        //   ]
        // ,
          city: [
            {required: true, message: '不能为空', trigger: 'blur' }
          ]
        ,
          openid: [
            {required: true, message: '不能为空', trigger: 'blur' }
          ]
        ,
          province: [
            {required: true, message: '不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/miniapp/shuser/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.username = data.shUser.username
                this.dataForm.userType = data.shUser.userType
                this.dataForm.mobile = data.shUser.mobile
                this.dataForm.password = data.shUser.password
                this.dataForm.email = data.shUser.email
                this.dataForm.integral = data.shUser.integral
                this.dataForm.balanceAmt = data.shUser.balanceAmt
                this.dataForm.lastLoginIp = data.shUser.lastLoginIp
                this.dataForm.birthday = data.shUser.birthday
                this.dataForm.lastLoginTime = data.shUser.lastLoginTime
                this.dataForm.userLevelId = data.shUser.userLevelId
                this.dataForm.nickname = data.shUser.nickname
                this.dataForm.weixinOpenid = data.shUser.weixinOpenid
                this.dataForm.gender = data.shUser.gender
                this.dataForm.avatarUrl = data.shUser.avatarUrl
                this.dataForm.city = data.shUser.city
                this.dataForm.openid = data.shUser.openid
                this.dataForm.province = data.shUser.province
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
        this.$http({
          url: this.$http.adornUrl(`/miniapp/shuser/${!this.dataForm.id ? 'save' : 'update'}`),
          method: 'post',
          data: this.$http.adornData({
            'id': this.dataForm.id,		
            'username': this.dataForm.username,		
            'userType': this.dataForm.userType,		
            'mobile': this.dataForm.mobile,		
            'password': this.dataForm.password,		
            'email': this.dataForm.email,		
            'integral': this.dataForm.integral,		
            'balanceAmt': this.dataForm.balanceAmt,		
            'lastLoginIp': this.dataForm.lastLoginIp,		
            'birthday': this.dataForm.birthday,		
            'lastLoginTime': this.dataForm.lastLoginTime,		
            'userLevelId': this.dataForm.userLevelId,		
            'nickname': this.dataForm.nickname,		
            'weixinOpenid': this.dataForm.weixinOpenid,		
            'gender': this.dataForm.gender,				
            'avatarUrl': this.dataForm.avatarUrl,		
            'city': this.dataForm.city,		
            'openid': this.dataForm.openid,		
            'province': this.dataForm.province          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
              onClose: () => {
                this.visible = false
                this.$emit('refreshDataList')
              }
            })
          } else {
            this.$message.error(data.msg)
          }
        })
          }
        })
      }
    }
  }
</script>
