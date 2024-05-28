import React, { useEffect, useState } from 'react';
import useCustomMove from '../../hooks/useCustomMove';
import { getList } from '../../api/todoApi';
import PageComponent from '../common/PageComponent';

const initState = {
  dtoList: [], // db 목록 전체
  pageNumList: [], // page 번호 리스트
  pageRequestDTO: null, // 현재 페이지랑 사이즈
  prev: false, // 전 페이지 있음?
  next: false, // 다음 페이지 잇음?
  totalCount: 0, // 총 개시물 갯수
  prevPage: 0, // 전 페이지 리스트 있음?
  nextPage: 0, // 다음 페이지 리스트 있음?
  totalPage: 0, // 총 몇 페이지 있나
  current: 0 // 현재 몇 페이지인가
}

const ListComponent = () => {
  const {page, size, refresh, moveToList, moveToRead} = useCustomMove()
  const [serverData, setServerData] = useState(initState)

  useEffect(() => {
    getList({page, size}).then(data => {
      console.log(data)
      setServerData(data)
    })
  }, [page, size, refresh])

  return (
    <div className='border-2 border-blue-100 mt-10 mr-2 ml-2'>
      <div className='flex flex-wrap mx-auto justify-center p-6'>
        {serverData.dtoList.map(todo => 
          <div 
          key={todo.tno} 
          className='w-full min-w-[400px] p-2 m-2 rounded shadow-md cursor-pointer'
          onClick={() => moveToRead(todo.tno)}
          >
            <div className='flex'>
              <div className='font-extrabold text-2xl p-2 w-1/12'>
                {todo.tno}
              </div>
              <div className='text-1xl m-1 p-2 w-8/12 font-extrabold'>
                {todo.title}
              </div>
              <div className='text-1xl m-1 p-2 w-8/12 font-medium'>
                {todo.dueDate}
              </div>
            </div>
          </div>
        )}
      </div>

      <PageComponent serverData={serverData} movePage={moveToList} />
    </div>
  );
};

export default ListComponent;