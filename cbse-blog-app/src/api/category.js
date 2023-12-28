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

export function saveCategory(avatar, categoryname) {
  const data = {
    avatar,
    categoryname
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

