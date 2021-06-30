课程发布
<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="3" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="最终发布"/>
    </el-steps>

    <div class="ccInfo">
      <img :src="coursePublish.cover">
      <div class="main">
        <h2>{{ coursePublish.title }}</h2>
        <p class="gray"><span>共{{ coursePublish.lessonNum }}课时</span></p>
        <p><span>所属分类：{{ coursePublish.oneSubject }} — {{ coursePublish.twoSubject }}</span></p>
        <p>课程讲师：{{ coursePublish.teacherName}}</p>
        <p class="price">￥{{ coursePublish.price }}</p>
      </div>
    </div>

    <div>
      <el-button @click="previous">返回修改</el-button>
      <el-button :disabled="saveBtnDisabled" type="primary" @click="publish">发布课程</el-button>
    </div>
  </div>

</template>

<script>
import course from '@/api/edu/course'

export default {
  data() {
    return {
      saveBtnDisabled: false, // 保存按钮是否禁用
      coursePublish:{}, //课程发布信息
      courseId:0 //课程id
    }
  },

  created() {
      console.log("create publish"+this.saveBtnDisabled)
      //得到路径中的id
      this.courseId = this.$route.params.id
      //刷新页面
      this.getPublishCourseInfo()
  },

  

  methods: {
    //得到课程发布的信息
    getPublishCourseInfo(){
      course.getPublishCourseInfo(this.courseId)
        .then(response =>{
          this.coursePublish = response.data.info
        })
    },
    //上一步
    previous() {
      this.$router.push({ path: '/course/chapter/'+this.courseId })
    },

    //课程发布
    publish() {
      //弹框提示
      this.$confirm('是否确认继续发布课程 ?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(()=>{
          //设置不允许再点击弹框
            this.saveBtnDisabled=true
            
            course.publishCourse(this.courseId)
              .then(()=>{
                //提示信息
                this.$message({
                  type:'success',
                  message:'课程发布成功'
                })
                //跳转
                this.$router.push({ path: '/course/list' })
              })
        })
      
      
    }
  }
}
</script>
<style scoped>
.ccInfo {
    background: #f5f5f5;
    padding: 20px;
    overflow: hidden;
    border: 1px dashed #DDD;
    margin-bottom: 40px;
    position: relative;
}
.ccInfo img {
    background: #d6d6d6;
    width: 500px;
    display: block;
    float: left;
    border: none;
}
.ccInfo .main {
    margin-left: 520px;
}

.ccInfo .main h2 {
    font-size: 28px;
    margin-bottom: 30px;
    line-height: 1;
    font-weight: normal;
}
.ccInfo .main p {
    margin-bottom: 10px;
    word-wrap: break-word;
    line-height: 24px;
    max-height: 48px;
    overflow: hidden;
}

.ccInfo .main .price {
    /* margin-top:20px; */
    top: 180px;
    left: 540px;
    bottom: 20px;
    line-height: 1;
    font-size: 28px;
    color: #d32f24;
    font-weight: normal;
    position: absolute;
}
</style>