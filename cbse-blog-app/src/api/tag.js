import request from '@/request'

export function getAllTags() {
  return request({
    url: '/tags',
    method: 'get'
  })
}

export function getAllTagsDetail(keyword) {
  return request({
    url: '/tags/detail',
    method: 'get',
    params: {
      keyword: keyword
    }
  })
}

export function getHotTags() {
  return request({
    url: '/tags/hot',
    method: 'get'
  })
}

export function getTag(id) {
  return request({
    url: `/tags/${id}`,
    method: 'get'
  })
}

export function getTagDetail(id) {
  return request({
    url: `/tags/detail/${id}`,
    method: 'get'
  })
}

export function saveTag(avatar, tagname) {
  const data = {
    avatar,
    tagname
  }
  return request({
    url: '/tags/create',
    method: 'post',
    data
  })
}

export function searchTags(query, page) {
  return request({
    url: '/tags',
    method: 'get',
    params: {
      pageNumber: page.pageNumber,
      pageSize: page.pageSize,
      name: page.name,
      sort: page.sort,
      year: query.year,
      month: query.month,
      tagId: query.tagId,
      categoryId: query.categoryId,
      keyword: query.keyword
    }
  })
}

export function mergeTag(oldIDLists, newName) {
  const data = {
    oldIDLists,
    newName
  }
  return request({
    url: '/tags/merge',
    method: 'post',
    data
  })
}

export function editTag(avatar, tagname, id) {
  const data = {
    id,
    avatar,
    tagname
  }
  return request({
    url: '/tags/update',
    method: 'post',
    data
  })
}