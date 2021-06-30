<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /课程列表 开始 -->
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">全部课程</span>
        </h2>
      </header>
      <section class="c-sort-box">
        <section class="c-s-dl">
          <dl>
            <dt>
              <span class="c-999 fsize14">课程类别</span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li>
                  <a title="全部" href="#" @click="searchAll()" :class="{active:oneIndex == -1}">全部</a>
                </li>
                <!-- 当oneIndex == index 时active样式生效 -->
                <li v-for="(item,index) in subjectNestedList" :key="index" :class="{active:oneIndex == index}">
                  <a :title="item.title" href="#" @click="searchOne(index)">{{item.title}}</a>
                </li>
              </ul>

            </dd>
          </dl>
          <dl>
            <dt>
              <span class="c-999 fsize14"></span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <!-- 二级分类 -->
                <li v-for="(item,index) in subSubjectList" :key="index">
                  <a :title="item.title" href="#" @click="searchTwo(index)" :class="{active:twoIndex == index}">{{item.title}}</a>
                </li>
              </ul>
            </dd>
          </dl>
          <div class="clear"></div>
        </section>
        <div class="js-wrap">
          <section class="fr">
            <span class="c-ccc">
              <i class="c-master f-fM">1</i>/
              <i class="c-666 f-fM">1</i>
            </span>
          </section>
          <section class="fl">
          <ol class="js-tap clearfix">
            <li :class="{'current bg-orange':buyCountSort!=''}">
              <a title="销量" href="javascript:void(0);" @click="searchBuyCount()">销量
              <span :class="{hide:buyCountSort==''}">↓</span>
              </a>
            </li>
            <li :class="{'current bg-orange':gmtCreateSort!=''}">
              <a title="最新" href="javascript:void(0);" @click="searchGmtCreate()">最新
              <span :class="{hide:gmtCreateSort==''}">↓</span>
              </a>
            </li>
            <li :class="{'current bg-orange':priceSort!=''}">
              <a title="价格" href="javascript:void(0);" @click="searchPrice()">价格&nbsp;
                <span :class="{hide:priceSort==''}">↓</span>
              </a>
            </li>
          </ol>
        </section>
        </div>
        <div class="mt40">
          <!-- /无数据提示 开始-->
          <section class="no-data-wrap" v-if="data.total == 0">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- /无数据提示 结束-->
          <article class="comm-course-list" v-if="data.total >0">
            <ul class="of" id="bna">
              <li v-for="item in data.items" :key="item.id">
                <div class="cc-l-wrap">
                  <section class="course-img">
                    <img :src="item.cover" class="img-responsive" :alt="item.title">
                    <div class="cc-mask">
                      <a :href="'/course/'+item.id" title="开始学习" class="comm-btn c-btn-1">开始学习</a>
                    </div>
                  </section>
                  <h3 class="hLh30 txtOf mt10">
                    <a :href="'/course/'+item.id" :title="item.title" class="course-title fsize18 c-333">{{item.title}}</a>
                  </h3>
                  <section class="mt10 hLh20 of">
                    <!--Number(i)  将i转为数字  -->
                    <span class="fr jgTag bg-green" v-if="Number(item.price) === 0">
                      <i class="c-fff fsize12 f-fA">免费</i>
                    </span>
                    <span class="fl jgAttr c-ccc f-fA">
                      <i class="c-999 f-fA">9634人学习</i>
                      |
                      <i class="c-999 f-fA">9634评论</i>
                    </span>
                  </section>
                </div>
              </li>
            </ul>
            <div class="clear"></div>
          </article>
        </div>
        <!-- 公共分页 开始 -->
        <div>
  <div class="paging">
    <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
    <a
      :class="{undisable: !data.hasPrevious}"
      href="#"
      title="首页"
      @click.prevent="gotoPage(1)">首</a>
    <a
      :class="{undisable: !data.hasPrevious}"
      href="#"
      title="前一页"
      @click.prevent="gotoPage(data.current-1)">&lt;</a>
    <a
      v-for="page in data.pages"
      :key="page"
      :class="{current: data.current == page, undisable: data.current == page}"
      :title="'第'+page+'页'"
      href="#"
      @click.prevent="gotoPage(page)">{{ page }}</a>
    <a
      :class="{undisable: !data.hasNext}"
      href="#"
      title="后一页"
      @click.prevent="gotoPage(data.current+1)">&gt;</a>
    <a
      :class="{undisable: !data.hasNext}"
      href="#"
      title="末页"
      @click.prevent="gotoPage(data.pages)">末</a>
    <div class="clear"/>
  </div>
</div>
        <!-- 公共分页 结束 -->
      </section>
    </section>
    <!-- /课程列表 结束 -->
  </div>
</template>
<script>
import courseApi from '@/api/course'
export default {

  data(){
    return{
      page:1, //当前页
      data:{}, //课程列表
      subjectNestedList: [], // 一级分类列表
      subSubjectList: [], // 二级分类列表
      searchObj: {}, // 查询表单对象
      oneIndex:-1,
      twoIndex:-1,
      buyCountSort:"",
      gmtCreateSort:"",
      priceSort:""
    }
  },
  created(){
    //查询第一页
    this.gotoPage(1)
    this.initSubject()
  },
  methods:{
    //查询分页
    gotoPage(page){
      courseApi.getFrontCourseList(page,8,this.searchObj)
        .then(response=>{
          this.data = response.data.data
        })
    },

    //查询所有一级分类进行显示
    initSubject(){
      courseApi.getAllSubject()
        .then(response=>{
          this.subjectNestedList = response.data.data.list
        })
    },
  //点击一级分类，查询对应二级分类
    searchOne(index){
      //清除查询信息
      this.deleteSearchObj()

      //为了使active样式生效
      this.oneIndex = index

      var item =this.subjectNestedList[index]
      //点击一级分类进行条件查询
      this.searchObj.subjectParentId = item.id
      this.gotoPage(1)

      //显示二级分类
      this.subSubjectList = item.children
    },

    //点击二级分类
    searchTwo(index){
      //清除查询条件
      this.deleteSearchObj()

      //为了当点击时让active样式生效
      this.twoIndex = index
      var item = this.subSubjectList[index]

      //点击二级分类，传递二级分类id进行查询
      this.searchObj.subjectId = item.id
      this.gotoPage(1)
    },

    //点击全部
    searchAll(){

      //清除查询条件
      this.deleteSearchObj()
      this.subSubjectList = []

      this.gotoPage(1)
    },

    //点击关注度排序
    searchBuyCount(){
      this.deleteOnSort()

      this.buyCountSort = '1'
      this.searchObj.buyCountSort = '1'

      this.gotoPage(1)
    },
    //点击最新排序
    searchGmtCreate(){
      this.deleteOnSort()

      this.gmtCreateSort = '1'
      this.searchObj.gmtCreateSort = '1'

      this.gotoPage(1)
    },
    //点击价格
    searchPrice(){
      this.deleteOnSort()

      this.priceSort = '1'
      this.searchObj.priceSort = '1'

      this.gotoPage(1)
    },

    //清除查询条件 ,仅一级二级分类可用
    deleteSearchObj(){
      this.searchObj = {}
      this.oneIndex=-1
      this.twoIndex=-1
      this.buyCountSort=""
      this.gmtCreateSort=""
      this.priceSort=""
    },

    //清除查询条件，排序可用
    deleteOnSort(){
      this.searchObj.buyCountSort = ''
      this.searchObj.gmtCreateSort = ''
      this.searchObj.priceSort = ''

      this.buyCountSort=""
      this.gmtCreateSort=""
      this.priceSort=""

    }
  }
};
</script>
<style scoped>
  .active {
    background: #bdbdbd;
  }
  .hide {
    display: none;
  }
  .show {
    display: block;
  }
</style>