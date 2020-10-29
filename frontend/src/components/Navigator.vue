<template>
     <v-card
    height="660"
    width="256"
  >
        <v-navigation-drawer permanent="permanent">
            <v-list-item>
                <v-list-item-content>
                    <v-list-item-title class="title">
                        Application
                    </v-list-item-title>
                    <v-list-item-subtitle>
                        subtext
                    </v-list-item-subtitle>
                </v-list-item-content>
            </v-list-item>

            <v-divider></v-divider>

            <v-list dense="dense" nav="nav">
                <v-list-item v-for="item in items" :key="item.title" link="link">
                    <v-list-item-icon>
                        <v-icon>{{ item.icon }}</v-icon>
                    </v-list-item-icon>

                    <v-list-item-content>
                        <v-list-item-title>{{ item.title }}</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
            </v-list>
        </v-navigation-drawer>
    </v-card>
</template>

<script>
    export default {
        data() {
            return {
                items: [
                    {
                        title: 'Dashboard',
                        icon: 'mdi-view-dashboard'
                    }, {
                        title: 'Photos',
                        icon: 'mdi-image'
                    }, {
                        title: 'About',
                        icon: 'mdi-help-box'
                    }
                ],
                right: null
            }
        }
    }
</script>

<style>
    .container {
        height: 100%;
    }
</style>