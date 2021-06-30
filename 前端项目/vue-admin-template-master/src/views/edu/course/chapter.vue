课程大纲
<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="最终发布"/>
    </el-steps>

    <el-button @click="OpenaddChapter" type="text">添加章节</el-button>
    <!-- 章节 -->
    <ul class="chanpterList">
        <li
            v-for="chapter in chapterVideoList"
            :key="chapter.id">
            <p>
                {{ chapter.title }}

                <span class="acts">
                    <el-button type="text" @click="openAddVideo(chapter.id)">添加小节</el-button>
                    <el-button style="" type="text" @click="openEditChapter(chapter.id)">编辑</el-button>
                    <el-button type="text" @click="deleteChapter(chapter.id)">删除</el-button>
                </span>
            </p>

            <!-- 小节 -->
            <ul class="chanpterList videoList">
                <li
                    v-for="video in chapter.children"
                    :key="video.id">
                    <p>{{ video.title }}
                        <span class="acts">
                            <el-button type="text" @click="openUpdateVideo(video.id)">编辑</el-button>
                            <el-button type="text" @click="deleteVideo(video.id)">删除</el-button>
                        </span>
                    </p>
                </li>
            </ul>
        </li>
    </ul>
    <div>
        <el-button @click="previous">上一步</el-button>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="next">下一步</el-button>
    </div>

        <!-- 添加和修改章节表单 -->
    <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
        <el-form :model="chapter" label-width="120px">
             <el-form-item label="章节标题">
               <el-input v-model="chapter.title"/>
            </el-form-item>
            <el-form-item label="章节排序">
                <el-input-number v-model="chapter.sort" :min="0" controls-position="right"/>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
        </div>
    </el-dialog>

    <!-- 添加和修改课时表单 -->
    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">
      <el-form :model="video" label-width="120px">
        <el-form-item label="课时标题">
          <el-input v-model="video.title"/>
        </el-form-item>
        <el-form-item label="课时排序">
          <el-input-number v-model="video.sort" :min="0" controls-position="right"/>
        </el-form-item>
        <el-form-item label="是否免费">
          <el-radio-group v-model="video.isFree">
            <el-radio :label= "1">免费</el-radio>
            <el-radio :label= "0">收费</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="上传视频">
          <el-upload
                :on-success="handleVodUploadSuccess"
                :on-remove="handleVodRemove"
                :before-remove="beforeVodRemove"
                :on-exceed="handleUploadExceed"
                :file-list="fileList"
                :action="BASE_API+'/eduVod/video/uplodeAliYunVideo'"
                :limit="1"
                class="upload-demo">
          <el-button size="small" type="primary">上传视频</el-button>
          <el-tooltip placement="right-end">
              <div slot="content">最大支持1G，<br>
                  支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
                  GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
                  MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
                  SWF、TS、VOB、WMV、WEBM 等视频格式上传</div>
              <i class="el-icon-question"/>
          </el-tooltip>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdateVideo">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import chapter from '@/api/edu/chapter'
import video from '@/api/edu/video'

export default {
  data(){
    return{
      saveBtnDisabled:false,
      chapterVideoList:[], //章节信息
      chapter:{
        courseId:0,//当前的课程id
        title:null,
        sort: 0
      },// 封装章节数据
      dialogChapterFormVisible:false, //显示章节弹框
      dialogVideoFormVisible:false,//显示小节弹框
      video:{
        chapterId:0,
        courseId:0,
        title: '',
        sort: 0,
        isFree: 0,
        videoSourceId: '', //视频id
        videoOriginalName:'' //视频名称
      }, //封装了小节数据

      fileList: [],//上传文件列表
      BASE_API: process.env.VUE_APP_BASE_API // 接口API地址
    }

  },
  created(){
    //设置章节里面的课程id
    this.chapter.courseId=this.$route.params.id

    //设置小节的课程id
    this.video.courseId = this.chapter.courseId

    //初始化数据
    this.getChapterVideo()
    console.log("create chapter  "+this.saveBtnDisabled)
  },
  methods:{
    //============================上传视频

    //点击×的时候调用
    beforeVodRemove(file,fileList){
      return this.$confirm(`确定移除 ${ file.name }？`);
    },

    //点击确认删除时
    handleVodRemove(file,fileList){
      //调用接口删除视频的方法
      video.removeALiYunVideo(this.video.videoSourceId)
        .then(()=>{
          //提示信息
          this.$message({
            type:'success',
            message:'删除成功'
          })

          //清空文件列表
          this.video.videoSourceId = ''
          this.video.videoOriginalName = ''
          this.fileList = []
        })
    },

    //成功回调
    handleVodUploadSuccess(response, file, fileList) {
      //得到视频id
      this.video.videoSourceId = response.data.videoId
      //得到视频名称
      this.video.videoOriginalName = file.name
    },
    //视图上传多于一个视频
    handleUploadExceed(files, fileList) {
      this.$message.warning('想要重新上传视频，请先删除已上传的视频')
    },

    //=========================================小节操作

    //打开添加小节弹框
    openAddVideo(chapterId){
      //打开弹框前先初始化数据
      this.video.id = null
      this.video.title = ''
      this.video.sort = 0
      this.video.isFree = 0
      this.video.videoSourceId = ''
      this.video.videoOriginalName = ''
      this.fileList = []

      //显示添加小节弹框
      this.dialogVideoFormVisible = true
      //设置小节里面的章节id
      this.video.chapterId = chapterId
    },

    //添加或者修改小节
    saveOrUpdateVideo(){
      console.log(this.video)
      //判断是添加还是修改
      if(this.video.id){
        this.updateVideo()
      }else{
        this.addVideo()
      }
      //关闭弹框
      this.dialogVideoFormVisible = false
     
    },
    //添加小节
    addVideo(){
      video.addVideo(this.video)
      .then(response=>{
        //提示信息
        this.$message({
          type:'success',
          message:'添加小节成功'
        })
         //刷新页面
        this.getChapterVideo()
      })
    },
    //修改小节
    updateVideo(){
      video.updateVideo(this.video)
        .then(()=>{
          //提示信息
          this.$message({
            type:'success',
            message:'修改小节成功'
          })
           //刷新页面
          this.getChapterVideo()
        })
    },
    //删除小节
    deleteVideo(id){
       //弹框提示
      this.$confirm('此操作将永久删除该小节, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(()=>{
          video.deleteVideo(id)
            .then(()=>{
              //提示信息
              this.$message({
                type:'success',
                message:'删除成功'
              })
              //刷新界面
              this.getChapterVideo()
            })
        })
    },

    //打开修改小节弹框
    openUpdateVideo(id){
      //根据id查询到video信息进行回显
      video.findVideoById(id)
        .then(response=>{
          this.video = response.data.video

          //视频信息回调
          this.fileList = [{'name': this.video.videoOriginalName}]
          
          //打开弹框
          this.dialogVideoFormVisible = true
        })
    },

    //=====================================================对章节的操作
    //添加或者修改章节
    saveOrUpdate(){
      //判断是添加章节还是修改章节
      //当chapter中不存在id的时候为添加，存在id的时候为修改，因为mp在添加完数据后悔生成id
      if(this.chapter.id){
        this.updateChapter()
      }else{
        this.addChapter()
      }
    },

    //添加章节
    addChapter(){
      chapter.addChapter(this.chapter)
      .then(response=>{
        //关闭弹框
        this.dialogChapterFormVisible = false
        //提示信息
        this.$message({
          type:'success',
          message:'添加成功'
        })
        //刷新页面
        this.getChapterVideo()
      })
    },

    //修改章节
    updateChapter(){
      chapter.updateChapter(this.chapter)
      .then(response=>{
        //关闭弹框
        this.dialogChapterFormVisible = false
        //提示信息
        this.$message({
          type:'success',
          message:'修改成功'
        })
        //刷新页面
        this.getChapterVideo()
      })
    },

    //打开修改弹框，数据回显
    openEditChapter(chapterId){
      //查询得到章节信息
      chapter.findChapterById(chapterId)
        .then(response=>{
          //查询到之后回显,回显后的chapter中包含chapter的id
          this.chapter = response.data.chapter
          //打开弹框
          console.log(this.chapter.id)
          this.dialogChapterFormVisible = true
        })
    },

    //得到当前章节信息,刷新页面
    getChapterVideo(){
      chapter.getAllChapterVideo(this.chapter.courseId)
      .then(response=>{
        this.chapterVideoList = response.data.allChapterVideo
      })
      .catch(error=>{
        console.log(error)
      })
    },

    //返回上一步
    previous(){
      //返回进行数据回显
      this.$router.push({path:"/course/info/"+this.chapter.courseId})
    },

    //下一步
    next(){
      this.saveBtnDisabled=true
      this.$router.push({path:"/course/publish/"+this.chapter.courseId})
    },

    //打开添加章节信息的弹框
    OpenaddChapter(){
      //清空弹框
      this.chapter.title = null
      this.chapter.sort = 0
      this.dialogChapterFormVisible = true
    },
    
    //删除章节
    deleteChapter(chapterId){
      //弹框提示
      this.$confirm('此操作将永久删除该课程, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(()=>{
          //调用方法删除
            chapter.deleteChapter(chapterId)
              .then(response=>{
                //提示信息
                this.$message({
                  type:'success',
                  message:'删除成功'
                })
                //刷新页面
                this.getChapterVideo()
              })
        })
    }
  }
}
</script>

<style scoped>
.chanpterList{
    position: relative;
    list-style: none;
    margin: 0;
    padding: 0;
}
.chanpterList li{
  position: relative;
}
.chanpterList p{
  float: left;
  font-size: 20px;
  margin: 10px 0;
  padding: 10px;
  height: 70px;
  line-height: 50px;
  width: 100%;
  border: 1px solid #DDD;
}
.chanpterList .acts {
    float: right;
    font-size: 14px;
}

.videoList{
  padding-left: 50px;
}
.videoList p{
  float: left;
  font-size: 14px;
  margin: 10px 0;
  padding: 10px;
  height: 50px;
  line-height: 30px;
  width: 100%;
  border: 1px dotted #DDD;
}

</style>