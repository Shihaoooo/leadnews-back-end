<#--
  article.ftl
  Freemarker 模板：文章展示页（Vue3 + Axios + Element Plus）
  特性：
    - 使用 ${article?json_string} 安全注入服务器端 article 对象（方案 A）
    - 若无服务端数据则使用 axios 请求 /api/article/detail
    - 使用 Element Plus 组件（卡片、头像、Skeleton、按钮等）
    - CDN 引入（注释里说明如何切换为本地文件）
-->
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>${article.title!"文章详情"}</title>
  
  <link rel="stylesheet" href="./plugins/element-plus/index.css">
  <script src="./plugins/vue3/vue.global.js"></script>
  <script src="./plugins/element-plus/index.full.js"></script>
  <script src="./plugins/axios/axios.js"></script>

  <style>
    body {
      font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, serif; background:#f5f7fa; margin:0; }
    .page { max-width: 900px; margin: 28px auto; padding: 12px; }
    .article-card { padding: 24px; }
    .article-title { font-size: 28px; font-weight:600; margin:18px 0; color:#303133 }
    .article-content { font-size:16px; line-height:1.8; color:#3b3b3b }
    .meta-row { display:flex; gap:8px; align-items:center; color:#909399; font-size:13px }
    .tag-row { margin-top:8px }
    @media (max-width:600px){ .page{padding:8px} .article-title{font-size:22px} }
  </style>
</head>
<body>
  <div id="app" class="page">
    <el-card class="article-card" shadow="always">
      <template #header>
        <div style="display:flex; justify-content:space-between; align-items:center; gap:12px;">
          <div style="display:flex; align-items:center; gap:12px;">
            <el-avatar :size="56" :src="article.avatarUrl" alt="头像"></el-avatar>
            <div>
              <div style="font-weight:600; color:#303133">{{ article.author || '匿名作者' }}</div>
              <div class="meta-row">{{ formatDate(article.publishTime) }}</div>
            </div>
          </div>
          <div style="display:flex; gap:8px; align-items:center;">
            <el-button type="primary" plain @click="onRefresh">刷新</el-button>
            <el-button type="text" @click="onShare">分享</el-button>
          </div>
        </div>
      </template>

      <div v-if="loading">
        <el-skeleton :rows="4" animated />
      </div>

      <div v-else>
        <div class="article-title">{{ article.title || '（无标题）' }}</div>

        <el-divider></el-divider>

        <div class="article-content" v-html="article.content || '<p>无内容</p>'"></div>

        <div style="margin-top:18px; display:flex; justify-content:space-between; align-items:center;">
          <div class="tag-row">
            <el-tag v-for="tag in article.tags || []" :key="tag" type="info" size="small" style="margin-right:6px">{{ tag }}</el-tag>
          </div>
          <div style="color:#909399; font-size:13px">发布时间：{{ formatDate(article.publishTime) }}</div>
        </div>
      </div>

    </el-card>
  </div>

  <script>
    const serverArticle = ${article?json_string};

    const App = {
      setup() {
        const { ref, onMounted } = Vue;
        const loading = ref(true);
        const article = ref({ title:'', author:'', avatarUrl:'', publishTime:'', content:'', tags:[] });

        const formatDate = (dateStr) => {
          if (!dateStr) return '';
          const d = new Date(dateStr);
          if (isNaN(d)) return dateStr || '';
          return d.toLocaleString('zh-CN', { year:'numeric', month:'short', day:'numeric', hour:'2-digit', minute:'2-digit' });
        };

        const loadFromServerVar = () => {
          if (serverArticle && typeof serverArticle === 'object') {
            article.value = serverArticle;
            loading.value = false;
            return true;
          }
          return false;
        };

        const fetchArticle = async () => {
          loading.value = true;
          try {
            const res = await axios.get('/api/article/detail');
            // 假定后端返回 { title, author, avatarUrl, publishTime, content, tags }
            article.value = res.data || {};
          } catch (e) {
            console.error('fetchArticle error', e);
            // 可用 Element Plus 的消息提示
            if (typeof ElMessage !== 'undefined') ElMessage.error('获取文章失败');
          } finally {
            loading.value = false;
          }
        };

        const onRefresh = () => fetchArticle();
        const onShare = () => {
          // 简单的分享示例（复制链接）
          const url = window.location.href;
          navigator.clipboard && navigator.clipboard.writeText(url).then(()=>{
            if (typeof ElMessage !== 'undefined') ElMessage.success('已复制当前页面链接');
          });
        };

        onMounted(() => {
          if (!loadFromServerVar()) {
            // 若没有服务端注入，发起接口请求
            fetchArticle();
          }
        });

        return { article, loading, formatDate, onRefresh, onShare };
      }
    };

    const app = Vue.createApp(App);
    app.use(ElementPlus);
    app.mount('#app');
  </script>

</body>
</html>
