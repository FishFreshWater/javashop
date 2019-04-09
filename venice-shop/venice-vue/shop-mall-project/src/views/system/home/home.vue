<template>
  <div class="content--hm">
    <el-row :gutter="15">
      <el-col :span="6">
        <div @click="getPublishData" class="tj-panel">
          <span class="tj-panel__icn" style="background: #1481eb;">
            <icon-svg style="margin-top: 14px;margin-left: 12px;" name="publish"></icon-svg>
          </span>
          <div class="tj-panel__con">
            <p>发布订阅服务</p>
            <p>昨日：<span class="text-primary">5261</span></p>
            <p>总数：<span class="text-primary">132423</span></p>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div @click="getSysData" class="tj-panel">
          <span class="tj-panel__icn" style="background: #5dad2c;">
            <icon-svg name="setting"></icon-svg>
          </span>
          <div class="tj-panel__con">
            <p>系统请求</p>
            <p>昨日：<span class="text-primary">5261</span></p>
            <p>总数：<span class="text-primary">132423</span></p>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div @click="getServeData" class="tj-panel">
          <span class="tj-panel__icn" style="background: #f07f1e;">
            <icon-svg style="margin-top: 12px;" name="warning"></icon-svg>
          </span>
          <div class="tj-panel__con">
            <p>服务请求</p>
            <p>昨日：<span class="text-primary">5261</span></p>
            <p>总数：<span class="text-primary">132423</span></p>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div @click="getErrorData" class="tj-panel">
          <span class="tj-panel__icn" style="background: #674597;">
            <icon-svg name="alarm"></icon-svg>
          </span>
          <div class="tj-panel__con">
            <p>异常警告</p>
            <p>昨日：<span class="text-primary">5261</span></p>
            <p>总数：<span class="text-primary">132423</span></p>
          </div>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="15">
      <el-col :span="18">
        <div class="lcbox">
          <h2 class="lcbox-hd">数据显示</h2>
          <div class="lcbox-line">
            <line-chart :chart-data="lineChartData"></line-chart>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="link-card">
          <div class="link-card__hd">
            推荐服务
          </div>
          <ul class="link-card__lst">
            <li><router-link :to="{ name: 'demo1'}">单页面增删改查</router-link></li>
            <li><router-link :to="{ name: 'demo2'}">左侧树形条件</router-link></li>
            <li><router-link :to="{ name: 'demo3'}">配置页面</router-link></li>
          </ul>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="15">
      <el-col :span="24">
        <div class="blk--home">
          <div class="evt-panel--l">
            <div class="evt-panel__mn">
              <h3 class="evt-panel__hd">
                <icon-svg name="safe-evt"></icon-svg>
                <span>安全事件</span>
                <em>前24小时</em>
              </h3>
              <div class="clearfix">
                <div class="evt-panel__tj is-steady">
                  <h1>0</h1>
                  <p>环比 <icon-svg name="arrow-round-up"></icon-svg> 持平</p>
                </div>
                <div class="evt-panel__dl">
                  <p><a class="text-primary">0次</a> 异地登录成功</p>
                  <p><a class="text-primary">0次</a> 异地登录成功</p>
                  <p><a class="text-primary">0次</a> 异地登录成功</p>
                  <p><a class="text-primary">0次</a> 异地登录成功</p>
                </div>
              </div>
            </div>
          </div>
          <div class="evt-panel--r">
            <div class="evt-panel__mn">
              <h3 class="evt-panel__hd">
                <icon-svg name="bug"></icon-svg>
                <span>安全漏洞</span>
                <em>上一次扫描</em>
              </h3>
              <div class="clearfix">
                <div class="evt-panel__tj">
                  <h1>0</h1>
                  <p>环比 <icon-svg name="arrow-round-up"></icon-svg> 持平</p>
                </div>
                <div class="evt-panel__dl">
                  <p><a class="text-primary">0次</a> 异地登录成功</p>
                  <p><a class="text-primary">0次</a> 异地登录成功</p>
                  <p><a class="text-primary">0次</a> 异地登录成功</p>
                  <p><a class="text-primary">0次</a> 异地登录成功</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import api from "@/api";
import LineChart from "@/components/line-chart";

export default {
  components: {
    LineChart: LineChart
  },
  data() {
    return {
      lineChartData: {
        lineData: [0, 0, 0, 0, 0, 0, 0],
        xData: [
          "20180512",
          "20180513",
          "20180514",
          "20180515",
          "20180516",
          "20180517",
          "20180518"
        ],
        nameData: "最近七日打开次数"
      },
      publishData: {
        lastNum: 1,
        totalNum: 10
      },
      sysData: {
        lastNum: 2,
        totalNum: 20
      },
      serveData: {
        lastNum: 3,
        totalNum: 30
      },
      errorData: {
        lastNum: 0,
        totalNum: 5
      }
    };
  },
  methods: {
    getPublishData() {
      api.system.getPublishData().then(({ data }) => {
        if (data && data.code === 0) {
          console.log("获取发布订阅data", data);
          this.publishData.lastNum = data.result.lastNum;
          this.publishData.totalNum = data.result.totalNum;
          this.lineChartData = data.result.chartData;
        }
      });
    },
    getSysData() {
      api.system.getSysData().then(({ data }) => {
        if (data && data.code === 0) {
          console.log("获取系统请求data", data);
          this.sysData.lastNum = data.result.lastNum;
          this.sysData.totalNum = data.result.totalNum;
          this.lineChartData = data.result.chartData;
        }
      });
    },
    getServeData() {
      api.system.getServeData().then(({ data }) => {
        if (data && data.code === 0) {
          console.log("获取服务请求data", data);
          this.serveData.lastNum = data.result.lastNum;
          this.serveData.totalNum = data.result.totalNum;
          this.lineChartData = data.result.chartData;
        }
      });
    },
    getErrorData() {
      api.system.getErrorData().then(({ data }) => {
        if (data && data.code === 0) {
          console.log("获取异常警告data", data);
          this.errorData.lastNum = data.result.lastNum;
          this.errorData.totalNum = data.result.totalNum;
          this.lineChartData = data.result.chartData;
        }
      });
    }
  }
};
</script>
<style lang="scss" scoped>
.content--hm {
  .el-row {
    margin-bottom: 15px;
  }
}
.blk--home {
  background: #fff;
  border-radius: 3px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  width: 100%;
  overflow: hidden;
}
.evt-panel--l,
.evt-panel--r {
  padding: 18px 9px 18px 18px;
  width: 50%;
  float: left;
}
.evt-panel--r {
  padding: 18px 18px 18px 9px;
}
.evt-panel__mn {
  border: 1px solid #eee;
  padding: 15px;
}
.evt-panel__hd {
  margin: 0;
  margin-bottom: 15px;
  font-weight: normal;
  span {
    font-size: 14px;
    margin-left: 4px;
    margin-right: 10px;
  }
  .icon-svg {
    height: 18px;
    width: 18px;
    vertical-align: middle;
  }
  em {
    font-style: normal;
    font-size: 12px;
    color: #999;
  }
}
.evt-panel__tj {
  float: left;
  padding-left: 45px;
  width: 50%;
  h1 {
    color: #f38900;
    font-size: 36px;
    margin-top: 15px;
    line-height: 50px;
  }
  p {
    margin-top: 10px;
    font-size: 12px;
  }
  .icon-svg__arrow-round-up {
    height: 20px;
    width: 20px;
    vertical-align: middle;
    color: #f00;
  }
  &.is-steady {
    .icon-svg__arrow-round-up {
      transform: rotate(90deg);
      color: #5fb333;
    }
  }
}
.evt-panel__dl {
  float: left;
  width: 50%;
  p {
    line-height: 26px;
    font-size: 12px;
  }
}
.link-card {
  background: #fff;
  border-radius: 3px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  width: 100%;
}
.link-card__hd {
  line-height: 54px;
  background: #d7d7d7;
  padding: 0 24px;
  color: #494949;
  font-size: 18px;
}
.link-card__lst {
  width: 100%;
  li {
    padding: 0 24px;
    display: block;
    line-height: 50px;
    font-size: 14px;
    &:hover {
      color: #1481eb;
      background: #eaf6fe;
    }
  }
}
.content--hm {
  background: #e8ecf0;
  padding-right: 10px;
  padding-bottom: 10px;
  width: 100%;
}
.lcbox {
  padding: 10px 20px;
  height: 400px;
  width: 100%;
  background: #fff;
  border-radius: 3px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
.lcbox-hd {
  line-height: 30px;
  margin: 0;
  font-weight: normal;
  font-size: 14px;
}
.lcbox-line {
  margin: 0 5px;
}
.tj-panel {
  cursor: pointer;
  padding: 10px 10px 10px 20px;
  height: 120px;
  width: 100%;
  background: #fff;
  border-radius: 3px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
.tj-panel__icn {
  height: 70px;
  width: 70px;
  border-radius: 100%;
  float: left;
  margin: 15px 0;
  .icon-svg {
    color: #fff;
    height: 40px;
    width: 40px;
    display: block;
    margin: 15px;
  }
}
.tj-panel__con {
  margin: 10px 0;
  line-height: 26px;
  margin-left: 95px;
}
</style>
