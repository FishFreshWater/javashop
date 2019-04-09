<template>
  <div class="main">
    <div class="body">
      <el-row :gutter="20">
        <el-col :span="16"><el-button type="danger">我的模版</el-button></el-col>
        <el-col :span="8">
           <!-- <div >
            <el-input placeholder="请输入内容"  class="input-with-select">
              <el-button slot="append" icon="el-icon-search"></el-button>
            </el-input>
          </div> -->
        </el-col>
      </el-row>
        <el-row :gutter="20" class="my-model"> 
          <el-col :span="4" v-for="tmp in myTmpList" :key="tmp.id">
              <el-card :body-style="{ padding: '0px' }" class="item">
                <div class="ribbon ribbon-badge ribbon-danger" v-show="tmp.used == 1">
                  <span class="ribbon-inner">使用中</span>
                </div>
                <img height="280" :src="tmp.image.fullUrl" class="image">
                <div class="title">{{tmp.name}}</div>
                <div class="mask">
                  <div class="btns">
                    <el-button @click="editTemplate(tmp.id)" type="primary" size="mini" round>编辑模板</el-button>
                    <el-button v-show="tmp.used == 0" @click="useMyTmp(tmp.id)" type="success" size="mini" round>使用模板</el-button>
                    <el-button v-show="tmp.used == 0"  @click="delMyTmp(tmp.id)" type="info" size="mini" round>删除模板</el-button>

                    <!-- <a class="btn btn-success btn-block btn-sm" data-toggle="ajaxPost" data-confirm="确认使用此模板吗？" href="./index.php?c=company&amp;a=diytemp&amp;do=use&amp;siteid=2134">使用模板</a>
                    <a class="btn btn-default btn-block btn-sm delete" data-tid="2134">删除模板</a> -->
                  </div>
                </div>
              </el-card>
          </el-col>
      </el-row>
    </div>
    <div class="body">
        <el-button type="success">官方模版</el-button>
        <el-row :gutter="20" class="my-model">
          <el-col :span="3"  v-for="tmp in tmpList" :key="tmp.id" >
              <el-card :body-style="{ padding: '0px' }" class="item">
                <img height="260" :src="tmp.image.fullUrl" class="image">
                 <div class="title">{{tmp.name}}</div>
                 <div class="mask">
                  <div class="btns">
                    <el-button type="success" @click="createTmp(tmp.id)" size="mini" round>使用模板</el-button>
                  </div>
                </div>
              </el-card>
          </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import API from '@/api'
export default {
  data () {
    return {
      myTmpList:[], // 我的模板数据
      tmpList:[]
    }
  },
  created () {
    this.getTmpList();
    this.getMyTmpList();
  },
  methods: {
    getTmpList(){
      API.msdecoration.list().then(({data}) => {
        if (data && data.code === 0) {
          this.tmpList = data.items
        } else {
          this.tmpList = []
        }
      })
    },
    getMyTmpList(){
       API.msdecoration.userList().then(({data}) => {
        if (data && data.code === 0) {
          this.myTmpList = data.items
        } else {
          this.myTmpList = []
        }
      })
    },
    delMyTmp(id){
      API.msdecoration.del(id).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
              onClose: () => {
                this.getMyTmpList()
              }
            })
          } else {
            this.$message.error(data.msg)
          }
      })
    },
    createTmp(id){
      API.msdecoration.create(id).then(({data}) => {
          if (data && data.code === 0) {
            console.log(data)
            this.editTemplate(data.id);
          } else {
            this.$message.error(data.msg)
          }
      })
    },
    useMyTmp(id){
      API.msdecoration.use(id).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
              onClose: () => {
                this.getMyTmpList()
              }
            })
          } else {
            this.$message.error(data.msg)
          }
      })
    },
    editTemplate(id){ // 编辑模板，请传入模板id
      this.$router.push({ // 跳转到模板编辑页
        name: 'template',
        params: {
          id: id // 传入的模板id,先写死
        }
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
.main {
  min-height: 100%;
  width: 100%;
  min-width: 1180px;
  overflow: hidden;
}
.body {
  padding: 20px 20px;
}
.image {
  width: 100%;
  display: block;
}
.my-model{
  margin-top: 20px;
}
.item{
  position: relative;
  &:hover{
    .title,.mask{
      display: block;
    }
    .btns{
      display: flex;
    }
  } 
}
.item .title {
  display: none;
  width: 100%;
    height: 40px;
    position: absolute;
    bottom: 0;
    left: 0;
    color: #fff;
    font-size: 14px;
    text-align: center;
    line-height: 40px;
    z-index: 2;
    background: rgba(0, 0, 0, 0.6);
}
.item .mask {
  display: none;
    position: absolute;
    background: rgba(0, 0, 0, 0.6);
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    z-index: 1;
    text-align:center;
}   
.item .btns {
  display: none;
    height: 100%;
    width: 100%;
    position: absolute;
    top: 0;
    left: 0;
    text-align: center;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}
.btns /deep/ .el-button{
  display: block;
  width: 70%;
  margin: 0;
  margin-bottom: 10px;
}
.ribbon-badge {
    top: -2px;
    left: -2px;
    overflow: hidden;
}

.ribbon {
    position: absolute;
    top: -3px;
    left: -3px;
    width: 150px;
    height: 150px;
    text-align: center;
    background-color: transparent;
}
.ribbon-danger .ribbon-inner {
    background-color: #f44;
}

.ribbon-badge .ribbon-inner {
    left: -40px;
    width: 100%;
    -webkit-transform: rotate(-45deg);
    -ms-transform: rotate(-45deg);
    -o-transform: rotate(-45deg);
    transform: rotate(-45deg);
}
.ribbon-inner {
    position: absolute;
    top: 16px;
    left: 0;
    display: inline-block;
    height: 30px;
    padding-right: 20px;
    padding-left: 20px;
    line-height: 30px;
    color: #fff;
    white-space: nowrap;
    background-color: #526069;
}
</style>
