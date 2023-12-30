import request from '@/request'

export function getAllCategorys() {
  return request({
    url: '/categorys',
    method: 'get',
  })
}

export function getAllCategorysDetail(keyword) {
  return request({
    url: '/categorys/detail',
    method: 'get',
    params: {
      keyword: keyword
    }
  })
}

export function getCategory(id) {
  return request({
    url: `/categorys/${id}`,
    method: 'get',
  })
}

export function getCategoryDetail(id) {
  return request({
    url: `/categorys/detail/${id}`,
    method: 'get',
  })
}

export function saveCategory(avatar, categoryname, description) {
  const data = {
    avatar,
    categoryname,
    description
  }
  return request({
    url: '/categorys/create',
    method: 'post',
    data
  })
}

export function searchCategorys(query, page) {
  return request({
    url: '/categorys',
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

export function mergeCategory(oldIDLists, newName) {
  const data = {
    oldIDLists,
    newName
  }
  return request({
    url: '/categorys/merge',
    method: 'post',
    data
  })
}

export function editCategory(avatar, categoryname, id, description) {
  const data = {
    id,
    avatar,
    categoryname,
    description
  }
  return request({
    url: '/categorys/update',
    method: 'post',
    data
  })
}
