<template>
    <div class="admin-container">
        <!-- 侧边栏 -->
        <div class="sidebar">
            <ul>
                <!-- 可折叠的“文章”栏 -->
                <li @click="toggleArticles" class="expandable">
                    📚 文章管理
                    <span class="arrow">{{ isExpanded ? '▼' : '▶' }}</span>
                </li>
                <ul v-show="isExpanded" class="nested-list">
                    <li>
                        <router-link to="/admin/posts" active-class="active">📖 文章列表</router-link>
                    </li>
                    <li>
                        <router-link to="/admin/post-new" active-class="active">📝 写博文</router-link>
                    </li>
                    <li>
                        <router-link to="/admin/drafts" active-class="active">🔖 草稿</router-link>
                    </li>
                    <li>
                        <router-link to="/admin/trash" active-class="active">🗑️ 废纸篓</router-link>
                    </li>
                </ul>

                <!-- 其他单独的菜单项 -->
                <li>
                    <router-link to="/admin/comments" active-class="active">💬 评论管理{{ unreadCommentCount }}</router-link>
                </li>
                <li>
                    <router-link to="/admin/archives" active-class="active">📂 分类管理</router-link>
                </li>
            </ul>
        </div>

        <!-- 右侧内容区域 -->
        <div class="content">
            <router-view />
        </div>
    </div>
</template>

<script setup>
    import axios from 'axios';
    import { onMounted, ref } from 'vue';

    const unreadCommentCount = ref(0);

    const isExpanded = ref(true);

    const toggleArticles = () => {
        isExpanded.value = !isExpanded.value;
    }

    const loadComments = async() => {
        await axios.get('http://localhost:8080/admin/comments/unread')
        .then(response => {
            unreadCommentCount.value = response.data;
        })
        .catch(error => {
            console.error('请求评论列表失败:', error);
        });
    }

    const checkSession = async() => {
        try {
            // 向后端发送请求检查 session 是否有效
            await axios.get('http://localhost:8080/check-session');
        } catch (error) {
            if (error.response && error.response.status === 401) {
                window.location.href = '/login'; // 假设你的登录页面路径为 /login
            } else {
                alert('无法检查会话状态，请稍后再试');
            }
        }
    }

    onMounted(() => {
        document.title = '后台仪表盘';
        loadComments();
        checkSession();
    });
</script>

<style scoped>
.admin-container {
    display: flex;
    height: 100vh; /* 使父容器充满整个视口高度 */
    margin: 0; /* 去掉父容器的外边距 */
    padding: 0; /* 去掉父容器的内边距 */
}

.sidebar {
    width: 200px;
    height: 100vh; /* 让侧边栏高度充满整个视口 */
    position: fixed; /* 固定侧边栏 */
    top: 0; /* 确保从顶部开始 */
    left: 0; /* 确保从左边开始 */
    background: #f5f5f5;
    padding: 0; /* 去掉侧边栏的内边距 */
    display: flex;
    flex-direction: column;
    flex-shrink: 0;
    overflow-y: auto; /* 允许侧边栏滚动 */
}

.sidebar ul {
    list-style: none;
    padding: 0;
    margin: 0;
}

.sidebar li {
    padding: 10px;
    cursor: pointer;
    transition: background 0.3s;
    user-select: none; /* 禁止文本选择 */
}

.sidebar a {
    display: block;
    text-decoration: none;
    color: #333;
    padding: 10px;
    transition: background 0.3s;
}

.sidebar a:hover {
    background: #ddd;
}

.active {
    font-weight: bold;
    color: #007bff;
}
  
.content {
    margin-left: 200px; /* 让内容区域避开固定的侧边栏 */
    overflow-y: auto; /* 让内容独立滚动 */
    flex-grow: 1;
    padding: 20px;
    height: 100vh; /* 使内容区域高度充满整个视口 */
    box-sizing: border-box; /* 计算内边距时包含在宽高内 */
}

/* 控制展开部分的样式 */
.sidebar .nested-list {
    padding-left: 20px; /* 给子菜单一个缩进 */
}
</style>
