课程信息
<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="最终发布"/>
    </el-steps>

    <el-form label-width="120px">

        <el-form-item label="课程标题">
            <el-input v-model="courseInfo.title" placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"/>
        </el-form-item>

        <!-- 所属分类 TODO -->
        <el-form-item label="课程分类">
            <el-select
                v-model="courseInfo.subjectParentId"
                @change="subjectOneChange"
                placeholder="一级分类">
                <el-option
                v-for="subjectOne in subjectListOne"
                :key="subjectOne.id"
                :label="subjectOne.title"
                :value="subjectOne.id"/>
            </el-select>

            <el-select
                v-model="courseInfo.subjectId"
                placeholder="二级分类">
                <el-option
                v-for="subjectTwo in subjectListTwo"
                :key="subjectTwo.id"
                :label="subjectTwo.title"
                :value="subjectTwo.id"/>
            </el-select>
        </el-form-item>

        <!-- 课程讲师 TODO -->
        <el-form-item label="课程讲师">
            <el-select
                v-model="courseInfo.teacherId"
                placeholder="请选择">
                <el-option
                v-for="teacher in teacherList"
                :key="teacher.id"
                :label="teacher.name"
                :value="teacher.id"/>
            </el-select>
        </el-form-item>

        <el-form-item label="总课时">
            <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数"/>
        </el-form-item>

        <!-- 课程简介 TODO -->
        <el-form-item label="课程简介">
            <!-- <el-input id="textarea" type="textarea" v-model="courseInfo.description"></el-input> -->
            <tinymce :height="300" v-model="courseInfo.description"/>
        </el-form-item>

        <!-- 课程封面 TODO -->
        <el-form-item label="课程封面">

            <el-upload
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
                :action="BASE_API+'/eduOss/fileOss'"
                class="avatar-uploader">
                <img id='myimg' v-if="courseInfo.cover" :src="courseInfo.cover">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>

        </el-form-item>

        <el-form-item label="课程价格">
            <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元"/> 元
        </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存并下一步</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<style>
  #myimg {
    width: 250px;
  }

  #textarea{
      height: 200px;
  }
</style>

<script>
import course from '@/api/edu/course'
import teacher from '@/api/edu/teacher'
import subject from '@/api/edu/subject'
//导入Tinymce
import Tinymce from "@/components/Tinymce"



export default {
    //导入组件，对组件进行声明
    components: {
      Tinymce
    },
  data() {
    return {
      saveBtnDisabled: false ,// 保存按钮是否禁用
      courseInfo:{
        title: null,
        subjectId: null,//二级分类id
        subjectParentId:null,//一级分类id
        teacherId: null,
        lessonNum: 0,
        description: '',
        cover: 'https://edu-1011010.oss-cn-beijing.aliyuncs.com/cover/01.jpg',//默认封面
        price: 0
      }, //保存课程信息
      teacherList:[], //教师列表
      subjectListOne:[],//一级分类列表
      subjectListTwo:[],//二级分类列表
      BASE_API: process.env.VUE_APP_BASE_API // 接口API地址
    }
  },

  created() {
        this.findAllTeacher() //初始化讲师列表
        this.getSubjectList() //初始化分类列表
      
      //判断路由中是否存在id，来判断是否进行回显
      if(this.$route.params && this.$route.params.id){
          this.courseInfo.id = this.$route.params.id
          //如果存在则根据id查询到信息来进行回显
          this.getCourseInfoById()
      }  
  },

  methods: {
    saveOrUpdate(){
        //判断是进行修改还是添加
        if(this.courseInfo.id){
            this.updateCourseInfo()
        }else{
            this.addCourseInfo()
        }
        this.saveBtnDisabled=true //设置按钮禁用

       
    },
    //添加课程信息
    addCourseInfo(){
        //调用接口存储数据
        course.addCourseInfo(this.courseInfo)
        .then(response=>{
            
            //提示信息
            this.$message({
                type:"success",
                message:"添加课程信息成功"
            })

             //无论是修改还是添加都需要进行跳转
            this.$router.push({ path: `/course/chapter/${response.data.id}` })
        })
        .catch(error=>{
             this.$message({
                type:"error",
                message:"添加课程信息失败"+error
            })
        })
    },
    //修改课程信息
    updateCourseInfo(){
        course.updateCourseInfo(this.courseInfo)
        .then(response=>{
            this.$message({
                type:'success',
                message:'修改成功'
            })
             //无论是修改还是添加都需要进行跳转
            this.$router.push({ path: `/course/chapter/`+this.courseInfo.id })
        })
        .catch(error=>{
             this.$message({
                type:'error',
                message:error
            })
        })
    },
    //得到所有讲师数据，封装到讲师列表
    findAllTeacher(){
        teacher.findAllTeacher()
        .then(response=>{
            this.teacherList = response.data.items
        })
        .catch(error=>{
            this.$message({
                type:"error",
                message:"查询讲师信息失败"+error
            })
        })
    },
    //得到所有一级二级分类
    getSubjectList(){
        subject.getSubjectList()
        .then(response=>{
            this.subjectListOne = response.data.list
        })
        .catch(error=>{
            this.$message({
                type:"error",
                message:"查询分类信息失败"+error
            })
        })
    },
    subjectOneChange(value){
        for(let i=0;i<this.subjectListOne.length;i++){
            if(this.subjectListOne[i].id === value ){
                this.subjectListTwo = this.subjectListOne[i].children
                this.courseInfo.subjectId=''
            }
        }
    },
    //上传封面成功
    handleAvatarSuccess(res, file) {
        console.log(res)// 上传响应
        console.log(URL.createObjectURL(file.raw))// base64编码
        this.courseInfo.cover = res.data.url
    },

    //上传之前
    beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg'
        const isLt2M = file.size / 1024 / 1024 < 2

        if (!isJPG) {
            this.$message.error('上传头像图片只能是 JPG 格式!')
        }
        if (!isLt2M) {
            this.$message.error('上传头像图片大小不能超过 2MB!')
        }
        return isJPG && isLt2M
    },

    //根据id查询课程信息进行回显
    getCourseInfoById(){
        course.getCourseInfoById(this.courseInfo.id)
            .then(response=>{
                console.log(response)

                this.courseInfo = response.data.courseInfo

                //遍历二级分类
                for(let i=0;i<this.subjectListOne.length;i++){
                    if(this.subjectListOne[i].id === this.courseInfo.subjectParentId){
                        this.subjectListTwo = this.subjectListOne[i].children
                    }
                }

                    
            })
            
    }
  }
  
}
</script>