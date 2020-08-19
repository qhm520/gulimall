<template>
  <div class="mod-attr-relation" style="margin-top: 10px;">
    <no-attr-related ref="noAttrRelated"></no-attr-related>
   <attr-related ref="attrRelated"></attr-related>
  </div>
</template>

<script>
  import AttrRelated from "./AttrRelated";
  import NoAttrRelated from "./NoAttrRelated";
  export default {
    name: 'AttrRelation',
    components: {
      AttrRelated,
      NoAttrRelated
    },
    data() {
      return {
        attrGroupId: 0
      }
    },
    methods: {
      init(attrGroupId) {
        this.$refs.attrRelated.queryAttrRelation(attrGroupId)
        this.$refs.noAttrRelated.queryNoAttrRelation(attrGroupId)
      }
    }
  }
</script>


<style scoped>

</style>

