<template>
    <Header/>
    <div class="post">
        <div name="article">
            <h1>{{article.title}}</h1>
            <div class="post-meta">{{ article.postDate }}</div>
            <div v-html="article.content" class="post-content"></div>       
        </div>
        <div class="post-foot">
            <div>最后修改：{{ article.updateDate }}</div>
            <button @click="like" class="like-buttion">👍 点赞</button>
        </div>
        <Comement style="margin-top: 20px;"/>
    </div>
    <Footer/>
</template>


<script setup>
    import { ref, onMounted, } from 'vue';
    import { useRoute } from 'vue-router'; // 确保导入 useRoute
    import Header from '@/components/Header.vue'
    import Footer from '@/components/Footer.vue'
    import Comement from '@/components/Comment.vue'
    import axios from 'axios'; // 你可以用 fetch 或其他库

    const article = ref({});     // 文章内容
    const route = useRoute();    // url路由
    const id = route.params.id;  // 获取URL中的id

    // 格式化日期
    const formatDate = (dateStr) => {
        const date = new Date(dateStr);
        const year = date.getFullYear().toString().padStart(4, '0');
        const month = (date.getMonth() + 1).toString().padStart(2, '0');
        const day = date.getDate().toString().padStart(2, '0');
        const result = `${year}年${month}月${day}日`;
        return result;
    };

    const like = () => {
        axios.put(`http://localhost:8080/articles/likes/${id}`, JSON.stringify(article.value.likes), {
            headers: {
                'Content-Type': 'application/json'
            }
        })
    };

    onMounted(async () => {
        try {
            const response = await axios.get(`http://localhost:8080/articles/${id}`); // 发起请求
            const data = response.data;

            // 处理返回的数据并赋值
            article.value = {
                title: data.title, // 显示文章标题
                content: data.content, // 显示文章内容（包含 HTML）
                postDate: formatDate(data.postDate),
                updateDate: formatDate(data.updateDate),
                views: data.views,
                likes: data.likes
            };
        } catch (error) {
            console.error('文章加载失败：', error); // 如果出错，打印错误
        }

        await axios.put(`http://localhost:8080/articles/views/${id}`, JSON.stringify(article.value.views), {
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .catch((error) => {
            console.error('文章浏览量增加失败：', error);
        });

        updateTitle();
    });

    // 设置页面标题
    const updateTitle = () => {
        document.title = `${article.value.title}`;
    };

</script>


<style scoped>
.post {
    position: relative;
    transform: translateX(-50%);
    left: 50%;
    width: 70vw;
}

.like-buttion {
    width: 70px;
    height: 35px;
    background-color: color;
    border: 1px solid red;
    margin-top: 10px;
}

.like-buttion:hover {
    background-color: #fb5235;
    color: white;
}

h1 {
    text-align: center;
}

.post-meta {
    text-align: center;
}

.post-content {
    margin-bottom: 20px;
}

.post-foot {
    text-align: right;
}
</style>