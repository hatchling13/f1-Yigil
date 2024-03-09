export const dynamic = 'force-dynamic';

import localFont from 'next/font/local';

import MSWComponent from '@/app/_components/MSWComponent';

import type { ReactNode } from 'react';
import type { Metadata } from 'next';

import './globals.css';
import NaverContext from '@/context/NaverContext';

const Pretendard = localFont({
  src: '../../public/fonts/PretendardVariable.woff2',
  display: 'swap',
});

export const metadata: Metadata = {
  title: '이길로그',

  // 추후 수정 필요
  description: 'Generated by create next app',
};

export default function RootLayout({ children }: { children: ReactNode }) {
  return (
    <html lang="ko" className={Pretendard.className}>
      <body className="max-w-[430px] mx-auto">
        <div id="modal"></div>

        <MSWComponent />
        <NaverContext ncpClientId={process.env.NAVER_MAPS_CLIENT_ID}>
          {children}
        </NaverContext>
      </body>
    </html>
  );
}
